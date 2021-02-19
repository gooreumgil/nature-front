package com.rainyheaven.nature.core.domain.itemsrc;

import com.rainyheaven.nature.core.domain.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ItemSrc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_src_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ImgType imgType;

    private String s3Key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

}
