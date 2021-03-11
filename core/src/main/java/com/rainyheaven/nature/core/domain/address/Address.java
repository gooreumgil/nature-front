package com.rainyheaven.nature.core.domain.address;

import com.rainyheaven.nature.core.domain.address.dto.app.AddressRequestDto;
import com.rainyheaven.nature.core.domain.base.BaseTimeEntity;
import com.rainyheaven.nature.core.domain.order.dto.app.OrderSaveRequestDto;
import com.rainyheaven.nature.core.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.access.vote.AbstractAclVoter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Address extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    private String main;
    private String detail;
    private String zipCode;
    private boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public static Address create(AddressRequestDto dto, boolean isDefault) {
        Address address = new Address();
        address.main = dto.getMainAddress();
        address.detail = dto.getDetailAddress();
        address.zipCode = dto.getZipCode();
        address.isDefault = isDefault;
        address.setCreatedDate(LocalDateTime.now());
        address.setLastModifiedDate(LocalDateTime.now());
        return address;
    }

    // 업데이트
    public void update(String mainAddress, String detailAddress, String zipCode) {
       this.main = mainAddress;
       this.detail = detailAddress;
       this.zipCode = zipCode;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
}
