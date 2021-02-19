package com.rainyheaven.nature.core.domain.item;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    private String nameKor;
    private String nameEng;
    private int price;
    private int discountPrice;
    private int stockQuantity;
    private int likesCount;
    private String description;
    private int capacity;
    private LocalDateTime registerAt;

}
