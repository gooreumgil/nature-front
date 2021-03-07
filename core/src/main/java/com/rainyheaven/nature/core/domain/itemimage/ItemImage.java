package com.rainyheaven.nature.core.domain.itemimage;

import com.rainyheaven.nature.core.domain.base.BaseTimeEntity;
import com.rainyheaven.nature.core.domain.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_image_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ImgType imgType;

    private String s3Key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

}
