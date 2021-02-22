package com.rainyheaven.nature.core.domain.user;

import com.rainyheaven.nature.core.domain.embedded.BirthDay;
import com.rainyheaven.nature.core.domain.embedded.PhoneNumber;
import com.rainyheaven.nature.core.domain.user.dto.app.UserSaveRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    @Embedded
    private PhoneNumber phoneNumber;

    @Embedded
    private BirthDay birthDay;

    public static User create(UserSaveRequestDto userSaveRequestDto) {
        User user = new User();
        user.email = userSaveRequestDto.getEmail();
        user.name = userSaveRequestDto.getName();
        user.password = userSaveRequestDto.getPassword();

        GregorianCalendar calendar = new GregorianCalendar();
        Date date = user.convertToDate(userSaveRequestDto.getBirthDay());
        calendar.setTime(date);
        user.birthDay = BirthDay.create(calendar);

        user.phoneNumber = PhoneNumber.create(userSaveRequestDto.getPhoneNumber());
        user.userRole = UserRole.USER;
        user.userStatus = UserStatus.ACTIVE;
        return user;
    }

    private Date convertToDate(String birthDay) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = originalFormat.parse(birthDay);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


}
