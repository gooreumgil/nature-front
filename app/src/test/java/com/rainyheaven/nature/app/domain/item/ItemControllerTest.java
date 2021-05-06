package com.rainyheaven.nature.app.domain.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainyheaven.nature.app.domain.category.CategoryFactory;
import com.rainyheaven.nature.app.domain.itemlike.ItemLikeFactory;
import com.rainyheaven.nature.app.domain.qna.QnaFactory;
import com.rainyheaven.nature.app.domain.review.ReviewFactory;
import com.rainyheaven.nature.app.domain.user.UserFactory;
import com.rainyheaven.nature.app.utils.TokenGenerator;
import com.rainyheaven.nature.core.domain.category.Category;
import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.itemlike.ItemLike;
import com.rainyheaven.nature.core.domain.qna.dto.app.QnaSaveRequestDto;
import com.rainyheaven.nature.core.domain.review.Review;
import com.rainyheaven.nature.core.domain.user.User;
import com.rainyheaven.nature.core.exception.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Autowired
    ItemLikeFactory itemLikeFactory;

    @Autowired
    QnaFactory qnaFactory;

    @Autowired
    ReviewFactory reviewFactory;

    @Autowired
    TokenGenerator tokenGenerator;

    @Autowired
    ObjectMapper objectMapper;

    private User user;


    @BeforeEach
    void beforeEach() {
        userFactory.deleteByEmail("test1@email.com");
        user = userFactory.createUser("test1@email.com", "testname", "password12", "password12", "01011112222", "20200220");


    }

    @DisplayName("1. 상품 가져오기")
    @Test
    void get_item() throws Exception {

        Category testCategory = categoryFactory.save("testCategory");
        Item item = itemFactory.saveWithCategoryItem(
                "한글이름",
                "englishName",
                "mainImgPath",
                10000,
                1000,
                500,
                "좋은 제품입니다.",
                100,
                testCategory.getName());


        mvc.perform(get("/v1/items/" + item.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("nameKor")))
                .andExpect(content().string(containsString("nameEng")))
                .andExpect(content().string(containsString(item.getNameKor())))
                .andExpect(content().string(containsString(item.getNameEng())));


    }

    @DisplayName("2. 상품 가져오기 실패 - 없는 상품")
    @Test
    void get_item_fail_not_exist() throws Exception {

        mvc.perform(get("/v1/items/" + 404L))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DomainException))
                .andExpect(content().string(containsString("NOT_EXIST_ITEM")));

    }

    @DisplayName("3. 상품 리스트 가져오기")
    @Test
    void get_item_list() throws Exception {

        itemFactory.listInit();

        mvc.perform(get("/v1/items/"))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @DisplayName("4. 상품 검색")
    @Test
    void item_search() throws Exception {

        Category testCategory = categoryFactory.save("testCategory");
        Item item = itemFactory.saveWithCategoryItem(
                "한글이름",
                "englishName",
                "mainImgPath",
                10000,
                1000,
                500,
                "좋은 제품입니다.",
                100,
                testCategory.getName());

        mvc.perform(get("/v1/items/")
                .param("keyword", item.getNameKor()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(item.getNameKor())));

    }

    @DisplayName("5. 상품 좋아요")
    @Test
    void item_like() throws Exception {

        Category testCategory = categoryFactory.save("testCategory");
        Item item = itemFactory.saveWithCategoryItem(
                "한글이름",
                "englishName",
                "mainImgPath",
                10000,
                1000,
                500,
                "좋은 제품입니다.",
                100,
                testCategory.getName());



        mvc.perform(post("/v1/items/" + item.getId() + "/item-likes")
                .header("Authorization", tokenGenerator.getToken(user)))
                .andExpect(status().isOk());


        Page<ItemLike> itemLikes = itemLikeFactory.find(user);
        assertEquals( 1, itemLikes.getTotalElements());

    }

    @DisplayName("6. 상품 좋아요 삭제")
    @Test
    void item_like_delete() throws Exception {

        Category testCategory = categoryFactory.save("testCategory");
        Item item = itemFactory.saveWithCategoryItem(
                "한글이름",
                "englishName",
                "mainImgPath",
                10000,
                1000,
                500,
                "좋은 제품입니다.",
                100,
                testCategory.getName());

        itemLikeFactory.save(item, user);

        mvc.perform(delete("/v1/items/" + item.getId() + "/item-likes")
                .header("Authorization", tokenGenerator.getToken(user)))
                .andExpect(status().isOk());

    }

    @DisplayName("7. 상품 qna get")
    @Test
    void item_qna_get() throws Exception {

        Category testCategory = categoryFactory.save("testCategory");
        Item item = itemFactory.saveWithCategoryItem(
                "한글이름",
                "englishName",
                "mainImgPath",
                10000,
                1000,
                500,
                "좋은 제품입니다.",
                100,
                testCategory.getName());

        qnaFactory.save(user, item, "좋은 제품인가요? 구매하고 싶은데 고민되네요1", true);
        qnaFactory.save(user, item, "좋은 제품인가요? 구매하고 싶은데 고민되네요2", true);


        mvc.perform(get("/v1/items/" + item.getId() + "/qnas")
                .header("Authorization", tokenGenerator.getToken(user)))
                .andExpect(status().isOk());

    }

    @DisplayName("8. 상품 qna 작성 성공")
    @Test
    void item_qna_write() throws Exception {

        Category testCategory = categoryFactory.save("testCategory");
        Item item = itemFactory.saveWithCategoryItem(
                "한글이름",
                "englishName",
                "mainImgPath",
                10000,
                1000,
                500,
                "좋은 제품입니다.",
                100,
                testCategory.getName());

        QnaSaveRequestDto qnaSaveRequestDto = new QnaSaveRequestDto("좋은 제품인가요? 구매하고 싶은데 고민되네요.", true);


        mvc.perform(post("/v1/items/" + item.getId() + "/qnas")
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(qnaSaveRequestDto)))
                .andExpect(status().isOk());

    }

    @DisplayName("9. 상품 qna 작성 실패 content null")
    @Test
    void item_qna_write_fail_content_null() throws Exception {

        Category testCategory = categoryFactory.save("testCategory");
        Item item = itemFactory.saveWithCategoryItem(
                "한글이름",
                "englishName",
                "mainImgPath",
                10000,
                1000,
                500,
                "좋은 제품입니다.",
                100,
                testCategory.getName());

        QnaSaveRequestDto qnaSaveRequestDto = new QnaSaveRequestDto(null, true);


        mvc.perform(post("/v1/items/" + item.getId() + "/qnas")
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(qnaSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("content")));

    }

    @DisplayName("10. 상품 qna 작성 실패 content short")
    @Test
    void item_qna_write_fail_content_short() throws Exception {

        Category testCategory = categoryFactory.save("testCategory");
        Item item = itemFactory.saveWithCategoryItem(
                "한글이름",
                "englishName",
                "mainImgPath",
                10000,
                1000,
                500,
                "좋은 제품입니다.",
                100,
                testCategory.getName());

        QnaSaveRequestDto qnaSaveRequestDto = new QnaSaveRequestDto("가", true);


        mvc.perform(post("/v1/items/" + item.getId() + "/qnas")
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(qnaSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("content")));

    }

    @DisplayName("11. 상품 qna 작성 실패 content long")
    @Test
    void item_qna_write_fail_content_long() throws Exception {

        Category testCategory = categoryFactory.save("testCategory");
        Item item = itemFactory.saveWithCategoryItem(
                "한글이름",
                "englishName",
                "mainImgPath",
                10000,
                1000,
                500,
                "좋은 제품입니다.",
                100,
                testCategory.getName());

        QnaSaveRequestDto qnaSaveRequestDto = new QnaSaveRequestDto("" +
                "가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가" +
                "가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가" +
                "가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가" +
                "가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가" +
                "가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가" +
                "가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가" +
                "가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가" +
                "가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가" +
                "가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가" +
                "가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가" +
                "가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가" +
                "가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가" +
                "가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가",
                true);


        mvc.perform(post("/v1/items/" + item.getId() + "/qnas")
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(qnaSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("content")));

    }

    @DisplayName("12. 상품 riview get")
    @Test
    void item_reviews_get() throws Exception {

        Category testCategory = categoryFactory.save("testCategory");
        Item item = itemFactory.saveWithCategoryItem(
                "한글이름",
                "englishName",
                "mainImgPath",
                10000,
                1000,
                500,
                "좋은 제품입니다.",
                100,
                testCategory.getName());

        Review review = reviewFactory.save(5, "정말 좋은 제품이네요. 재구매 하겠습니다.", item, user);

        mvc.perform(get("/v1/items/" + item.getId() + "/reviews"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(review.getContent())));



    }

    @DisplayName("12. 상품 qna count get")
    @Test
    void item_qna_total_get() throws Exception {

        Category testCategory = categoryFactory.save("testCategory");
        Item item = itemFactory.saveWithCategoryItem(
                "한글이름",
                "englishName",
                "mainImgPath",
                10000,
                1000,
                500,
                "좋은 제품입니다.",
                100,
                testCategory.getName());


        qnaFactory.save(user, item, "좋은 제품인가요? 구매하고 싶은데 고민되네요1", true);
        qnaFactory.save(user, item, "좋은 제품인가요? 구매하고 싶은데 고민되네요2", true);

//        reviewFactory.save(5, "정말 좋은 제품이네요. 재구매 하겠습니다1.", item, user);
//        reviewFactory.save(5, "정말 좋은 제품이네요. 재구매 하겠습니다2.", item, user);

        mvc.perform(get("/v1/items/" + item.getId() + "/count/qnas"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("2")));



    }

    @DisplayName("13. 상품 review count get")
    @Test
    void item_review_total_get() throws Exception {

        Category testCategory = categoryFactory.save("testCategory");
        Item item = itemFactory.saveWithCategoryItem(
                "한글이름",
                "englishName",
                "mainImgPath",
                10000,
                1000,
                500,
                "좋은 제품입니다.",
                100,
                testCategory.getName());

        reviewFactory.save(5, "정말 좋은 제품이네요. 재구매 하겠습니다1.", item, user);
        reviewFactory.save(5, "정말 좋은 제품이네요. 재구매 하겠습니다2.", item, user);

        mvc.perform(get("/v1/items/" + item.getId() + "/count/reviews"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("2")));


    }







}
