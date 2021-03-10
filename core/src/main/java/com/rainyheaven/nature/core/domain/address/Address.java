package com.rainyheaven.nature.core.domain.address;

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
    private Integer zipCode;
    private boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public static Address create(String main, String detail, Integer zipCode, boolean isDefault) {
        Address address = new Address();
        address.main = main;
        address.detail = detail;
        address.zipCode = zipCode;
        address.isDefault = isDefault;
        address.setCreatedDate(LocalDateTime.now());
        address.setLastModifiedDate(LocalDateTime.now());
        return address;
    }

    // 업데이트
    public void update(String mainAddress, String detailAddress, Integer zipCode) {
       this.main = mainAddress;
       this.detail = detailAddress;
       this.zipCode = zipCode;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
