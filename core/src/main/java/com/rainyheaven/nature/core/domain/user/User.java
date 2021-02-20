package com.rainyheaven.nature.core.domain.user;

import com.rainyheaven.nature.core.domain.user.dto.app.UserSaveRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String email;
    private String name;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private Integer points;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    public static User create(UserSaveRequestDto userSaveRequestDto) {
        User user = new User();
        user.email = userSaveRequestDto.getEmail();
        user.name = userSaveRequestDto.getName();
        user.password = userSaveRequestDto.getPassword();
        user.userRole = UserRole.USER;
        user.userStatus = UserStatus.ACTIVE;
        return user;
    }


}
