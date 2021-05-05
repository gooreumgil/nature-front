package com.rainyheaven.nature.app.domain.item;

import com.rainyheaven.nature.core.domain.category.Category;
import com.rainyheaven.nature.core.domain.category.CategoryRepository;
import com.rainyheaven.nature.core.domain.categoryitem.CategoryItem;
import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.item.ItemRepository;
import com.rainyheaven.nature.core.domain.item.dto.app.ItemSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemFactory {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public Item save(String nameKor, String nameEng, String mainImgPath, int price, int discountPrice, int stockQuantity, String description, int capacity) {
        ItemSaveRequestDto saveRequestDto = getSaveRequestDto(nameKor, nameEng, mainImgPath, price, discountPrice, stockQuantity, description, capacity);
        Item item = Item.create(saveRequestDto);
        return itemRepository.save(item);

    }

    public Item saveWithCategoryItem(String nameKor, String nameEng, String mainImgPath, int price, int discountPrice, int stockQuantity, String description, int capacity) {
        ItemSaveRequestDto saveRequestDto = getSaveRequestDto(nameKor, nameEng, mainImgPath, price, discountPrice, stockQuantity, description, capacity);
        Item item = Item.create(saveRequestDto);
        Category testCategory = categoryRepository.findByName("testCategory").get();
        CategoryItem categoryItem = CategoryItem.create(true);
        categoryItem.setItem(item);
        categoryItem.setCategory(testCategory);

        return itemRepository.save(item);

    }

    public List<Item> listInit() {

        List<Item> items = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            String s = String.valueOf(i);
            ItemSaveRequestDto saveRequestDto = getSaveRequestDto(
                    s + "한글이름",
                    s + "english name",
                    s + "mainImgPath",
                    i + 10000, i + 500,
                    i + 1000, s + "description",
                    i + 50
            );

            items.add(Item.create(saveRequestDto));
        }

        return itemRepository.saveAll(items);
    }


    public ItemSaveRequestDto getSaveRequestDto(String nameKor, String nameEng, String mainImgPath, int price, int discountPrice, int stockQuantity, String description, int capacity) {
        return new ItemSaveRequestDto(nameKor, nameEng, mainImgPath, price, discountPrice, stockQuantity, description, capacity);
    }

}
