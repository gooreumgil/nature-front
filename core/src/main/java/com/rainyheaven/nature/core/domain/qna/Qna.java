package com.rainyheaven.nature.core.domain.qna;

import com.rainyheaven.nature.core.domain.base.BaseTimeEntity;
import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.qna.dto.app.QnaSaveRequestDto;
import com.rainyheaven.nature.core.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Qna extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id")
    private Long id;


    @Column(columnDefinition = "TEXT")
    private String content;
    private boolean isSecret;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Enumerated(EnumType.STRING)
    private QnaStatus qnaStatus;

    public static Qna create(QnaSaveRequestDto dto, Item item, User user) {

        Qna qna = new Qna();
        qna.content = dto.getContent().trim();
        qna.isSecret = dto.isSecret();
        qna.setUser(user);
        qna.setItem(item);
        qna.qnaStatus = QnaStatus.WAIT;
        qna.setCreatedDate(LocalDateTime.now());
        qna.setLastModifiedDate(LocalDateTime.now());
        return qna;

    }

    // 연관관계 편의 메소드

    private void setUser(User user) {
        this.user = user;
        user.addQna(this);
    }

    // 연관관계 편의 메소드
    private void setItem(Item item) {
        this.item = item;
        item.addQna(this);
    }


}
