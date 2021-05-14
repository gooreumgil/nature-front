package com.rainyheaven.nature.core.domain.itemlike;

import com.rainyheaven.nature.core.domain.base.BaseTimeEntity;
import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static ItemLike create(Item item, User user) {
        ItemLike itemLike = new ItemLike();
        itemLike.setItem(item);
        itemLike.setUser(user);
        return itemLike;
    }

    private void setItem(Item item) {
        this.item = item;
        item.addItemLike(this);
    }

    private void setUser(User user) {
        this.user = user;
        user.addItemLike(this);
    }

}
