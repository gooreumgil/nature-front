package com.rainyheaven.nature.core.domain.user;

import com.rainyheaven.nature.core.domain.address.Address;
import com.rainyheaven.nature.core.domain.address.dto.app.AddressRequestDto;
import com.rainyheaven.nature.core.domain.user.dto.app.UserSaveRequestDto;
import com.rainyheaven.nature.core.utils.AES256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AES256Util aes256Util;

    public User findById(Long id) {
        return userRepository.findByIdAndUserStatus(id, UserStatus.ACTIVE).orElseThrow(RuntimeException::new);
    }

    public User findByIdWithAddress(Long id) {
        return userRepository.findByIdWithAddress(id).orElseThrow(RuntimeException::new);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmailAndUserStatus(aes256Util.encode(email), UserStatus.ACTIVE).orElseThrow(RuntimeException::new);
    }

    @Transactional
    public void save(UserSaveRequestDto userSaveRequestDto) {
        userSaveRequestDto.setEmail(aes256Util.encode(userSaveRequestDto.getEmail().trim()));
        userSaveRequestDto.setPassword(passwordEncoder.encode(userSaveRequestDto.getPassword().trim()));

        userRepository.save(User.create(userSaveRequestDto));
    }

    public void existCheck(Long id) {
        boolean exists = userRepository.existsById(id);
        if (!exists) throw new RuntimeException("존재하지 않는 유저입니다.");
    }

    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(aes256Util.encode(email));
    }

    public boolean checkTokenExpiredTime(Date expiration) {
        Date date = new Date();
        long currentTime = date.getTime();
        long expirationTime = expiration.getTime();

        if (currentTime > expirationTime) {
            throw new RuntimeException("세션이 만료되었습니다.");
        }

        long leftTime = expirationTime - currentTime;
        long refreshTime = (1000 * 60 * 60 * 24) * 3L;

        return leftTime < refreshTime;
    }


    public User authenticate(String email, String password) {
        User user = findByEmail(email);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException();
        }
        return user;
    }

    public void saveAddress(AddressRequestDto addressRequestDto, User user) {
        boolean registerDefaultAddress = addressRequestDto.isRegisterDefaultAddress();
        boolean registerNewAddress = addressRequestDto.isRegisterNewAddress();
        boolean useExistingAddress = addressRequestDto.isUseExistingAddress();

        // 기본 주소로 등록하기 O
        if (registerDefaultAddress) {

            // 기존의 address 중 default인 것을 false로 바꿈
            List<Address> addressList = user.getAddressList();

            if (!addressList.isEmpty()) {
                addressList.stream().filter(Address::isDefault)
                        .findFirst().ifPresent(address -> address.setIsDefault(false));
            }

            // 신규 배송지로 입력
            if (registerNewAddress) {
                user.addAddress(Address.create(addressRequestDto, true));
            }

            // 기존의 address 사용
            else if (useExistingAddress){
                addressList.stream().filter(address -> address.getId().equals(addressRequestDto.getExistingAddressId()))
                        .findFirst().ifPresent(address -> address.setIsDefault(true));
            }

            // 신규 배송지 x, 기존의 address 사용 x
            else {
                user.addAddress(Address.create(addressRequestDto, true));
            }

        }

        // 기본 주소로 등록 X
        else {

            // 신규 배송지 입력
            if (registerNewAddress) {
                user.addAddress(Address.create(addressRequestDto, false));
            }

        }
    }
}
