package com.rainyheaven.nature.app.domain.item;

import com.rainyheaven.nature.app.domain.category.CategoryFactory;
import com.rainyheaven.nature.app.domain.user.UserFactory;
import com.rainyheaven.nature.app.utils.TokenGenerator;
import com.rainyheaven.nature.core.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ItemControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ItemFactory itemFactory;

    @Autowired
    UserFactory userFactory;

    @Autowired
    CategoryFactory categoryFactory;

    private User user;

    @Autowired
    TokenGenerator tokenGenerator;

    @BeforeEach
    void beforeEach() {
        userFactory.deleteByEmail("test1@email.com");
        user = userFactory.createUser("test1@email.com", "testname", "password12", "password12", "01011112222", "20200220");
    }

    @DisplayName("1. 아이템 가져오기")
    @Test
    void get() {

        categoryFactory.save("testCategory");
        itemFactory.saveWithCategoryItem(
                "한글이름",
                "englishName",
                "mainImgPath",
                10000,
                1000,
                500,
                "좋은 제품입니다.", 100);

    }

}
