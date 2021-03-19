package com.rainyheaven.nature.core.domain.user;

import com.rainyheaven.nature.core.domain.address.Address;
import com.rainyheaven.nature.core.domain.base.BaseTimeEntity;
import com.rainyheaven.nature.core.domain.embedded.BirthDay;
import com.rainyheaven.nature.core.domain.embedded.PhoneNumber;
import com.rainyheaven.nature.core.domain.itemlike.ItemLike;
import com.rainyheaven.nature.core.domain.order.Order;
import com.rainyheaven.nature.core.domain.qna.Qna;
import com.rainyheaven.nature.core.domain.review.Review;
import com.rainyheaven.nature.core.domain.reviewlike.ReviewLike;
import com.rainyheaven.nature.core.domain.user.dto.app.UserSaveRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String email;
    private String name;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private int points;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Embedded
    private PhoneNumber phoneNumber;

    @Embedded
    private BirthDay birthDay;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addressList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ItemLike> itemLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ReviewLike> reviewLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Qna> qnaList = new ArrayList<>();

    public static User create(UserSaveRequestDto userSaveRequestDto) {
        User user = new User();
        user.email = userSaveRequestDto.getEmail();
        user.name = userSaveRequestDto.getName().trim();
        user.password = userSaveRequestDto.getPassword();

        GregorianCalendar calendar = new GregorianCalendar();
        Date date = user.convertToDate(userSaveRequestDto.getBirthDay().trim());
        calendar.setTime(date);
        user.birthDay = BirthDay.create(calendar);

        user.phoneNumber = PhoneNumber.create(userSaveRequestDto.getPhoneNumber().trim());
        user.userRole = UserRole.USER;
        user.userStatus = UserStatus.ACTIVE;
        user.setCreatedDate(LocalDateTime.now());
        user.setLastModifiedDate(LocalDateTime.now());
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

    // 연관관계 편의 메소드
    public void addOrders(Order order) {
        this.orders.add(order);
    }

    // 연관관계 편의 메소드
    public void addItemLike(ItemLike itemLike) {
        this.itemLikes.add(itemLike);
    }

    // 연관관계 편의 메소드
    public void addQna(Qna qna) {
        this.qnaList.add(qna);
    }

    // 연관관계 편의 메소드
    public void addReview(Review review) {
        this.reviews.add(review);
    }

    // 연관관계 편의 메소드
    public void addReviewLike(ReviewLike reviewLike) {
        this.reviewLikes.add(reviewLike);
    }
    // 구매 확정시 포인트 적립

    public void savePoints(int points) {
        this.points += points;
    }
    // 포인트 사용시 차감

    public void minusPoints(Integer usedPoints) {
        this.points -= usedPoints;
    }
    // 주문취소 & 환불시 사용한 포인트 돌려받음

    public void plusPoints(int usedPoints) {
        this.points += usedPoints;
    }

    // 주문시 주소 추가
    public void addAddress(Address address) {
        this.addressList.add(address);
        address.setUser(this);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
        setLastModifiedDate(LocalDateTime.now());
    }
}
