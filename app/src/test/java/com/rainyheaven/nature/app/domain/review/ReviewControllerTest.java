package com.rainyheaven.nature.app.domain.review;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainyheaven.nature.app.domain.item.ItemFactory;
import com.rainyheaven.nature.app.domain.order.OrderFactory;
import com.rainyheaven.nature.app.domain.user.UserFactory;
import com.rainyheaven.nature.app.utils.TokenGenerator;
import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.order.Order;
import com.rainyheaven.nature.core.domain.orderitem.OrderItem;
import com.rainyheaven.nature.core.domain.review.Review;
import com.rainyheaven.nature.core.domain.review.dto.app.ReviewSaveRequestDto;
import com.rainyheaven.nature.core.domain.reviewlike.ReviewLike;
import com.rainyheaven.nature.core.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;

import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ReviewControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ReviewFactory reviewFactory;

    @Autowired
    UserFactory userFactory;

    @Autowired
    OrderFactory orderFactory;

    @Autowired
    ItemFactory itemFactory;

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

    @DisplayName("1. review save 성공")
    @Test
    void save() throws Exception {

        List<Item> items = itemFactory.listInit();
        Order order = orderFactory.save(user, items);
        List<OrderItem> orderItems = order.getOrderItems();
        OrderItem orderItem = orderItems.get(0);

        ReviewSaveRequestDto reviewSaveRequestDto = reviewFactory.getReviewSaveRequestDto(5, "정말 좋은 제품이네요. 재구매 하고 싶습니다.");

        mvc.perform(post("/v1/reviews")
                .header("Authorization", tokenGenerator.getToken(user))
                .param("content", reviewSaveRequestDto.getContent())
                .param("rating", String.valueOf(reviewSaveRequestDto.getRating()))
                .param("itemId", String.valueOf(orderItem.getItem().getId()))
                .param("orderItemId", String.valueOf(orderItem.getId())))
                .andExpect(status().isOk());

    }

    @DisplayName("2. review save 실패 - rating 0점")
    @Test
    void save_fail_rating_zero() throws Exception {

        List<Item> items = itemFactory.listInit();
        Order order = orderFactory.save(user, items);
        List<OrderItem> orderItems = order.getOrderItems();
        OrderItem orderItem = orderItems.get(0);

        ReviewSaveRequestDto reviewSaveRequestDto = reviewFactory.getReviewSaveRequestDto(0, "정말 좋은 제품이네요. 재구매 하고 싶습니다.");

        mvc.perform(post("/v1/reviews")
                .header("Authorization", tokenGenerator.getToken(user))
                .param("content", reviewSaveRequestDto.getContent())
                .param("rating", String.valueOf(reviewSaveRequestDto.getRating()))
                .param("itemId", String.valueOf(orderItem.getItem().getId()))
                .param("orderItemId", String.valueOf(orderItem.getId())))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BindException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("rating")));

    }

    @DisplayName("3. review save 실패 - rating 5점 이상")
    @Test
    void save_fail_rating_bigger_than_five() throws Exception {

        List<Item> items = itemFactory.listInit();
        Order order = orderFactory.save(user, items);
        List<OrderItem> orderItems = order.getOrderItems();
        OrderItem orderItem = orderItems.get(0);

        ReviewSaveRequestDto reviewSaveRequestDto = reviewFactory.getReviewSaveRequestDto(6, "정말 좋은 제품이네요. 재구매 하고 싶습니다.");

        mvc.perform(post("/v1/reviews")
                .header("Authorization", tokenGenerator.getToken(user))
                .param("content", reviewSaveRequestDto.getContent())
                .param("rating", String.valueOf(reviewSaveRequestDto.getRating()))
                .param("itemId", String.valueOf(orderItem.getItem().getId()))
                .param("orderItemId", String.valueOf(orderItem.getId())))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BindException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("rating")));

    }

    @DisplayName("4. review save 실패 - content null")
    @Test
    void save_fail_content_null() throws Exception {

        List<Item> items = itemFactory.listInit();
        Order order = orderFactory.save(user, items);
        List<OrderItem> orderItems = order.getOrderItems();
        OrderItem orderItem = orderItems.get(0);

        ReviewSaveRequestDto reviewSaveRequestDto = reviewFactory.getReviewSaveRequestDto(5, null);

        mvc.perform(post("/v1/reviews")
                .header("Authorization", tokenGenerator.getToken(user))
                .param("content", reviewSaveRequestDto.getContent())
                .param("rating", String.valueOf(reviewSaveRequestDto.getRating()))
                .param("itemId", String.valueOf(orderItem.getItem().getId()))
                .param("orderItemId", String.valueOf(orderItem.getId())))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BindException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("content")));

    }

    @DisplayName("5. review save 실패 - content long")
    @Test
    void save_fail_content_long() throws Exception {

        List<Item> items = itemFactory.listInit();
        Order order = orderFactory.save(user, items);
        List<OrderItem> orderItems = order.getOrderItems();
        OrderItem orderItem = orderItems.get(0);

        ReviewSaveRequestDto reviewSaveRequestDto = reviewFactory.getReviewSaveRequestDto(5, reviewFactory.getLongContent());

        mvc.perform(post("/v1/reviews")
                .header("Authorization", tokenGenerator.getToken(user))
                .param("content", reviewSaveRequestDto.getContent())
                .param("rating", String.valueOf(reviewSaveRequestDto.getRating()))
                .param("itemId", String.valueOf(orderItem.getItem().getId()))
                .param("orderItemId", String.valueOf(orderItem.getId())))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BindException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("content")));

    }

    @DisplayName("6. review save 실패 - content short")
    @Test
    void save_fail_content_short() throws Exception {

        List<Item> items = itemFactory.listInit();
        Order order = orderFactory.save(user, items);
        List<OrderItem> orderItems = order.getOrderItems();
        OrderItem orderItem = orderItems.get(0);

        ReviewSaveRequestDto reviewSaveRequestDto = reviewFactory.getReviewSaveRequestDto(5, "t");

        mvc.perform(post("/v1/reviews")
                .header("Authorization", tokenGenerator.getToken(user))
                .param("content", reviewSaveRequestDto.getContent())
                .param("rating", String.valueOf(reviewSaveRequestDto.getRating()))
                .param("itemId", String.valueOf(orderItem.getItem().getId()))
                .param("orderItemId", String.valueOf(orderItem.getId())))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BindException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("content")));

    }

    @DisplayName("7. review 좋아요")
    @Test
    void review_like() throws Exception {

        Item item = itemFactory.save(
                "한글이름",
                "englishName",
                "mainImgPath",
                15000,
                1000,
                1000,
                "좋은상품이다",
                100
        );

        Review review = reviewFactory.save(5, "정말 좋은 제품이네요. 재구매할 의향이 있습니다.", item, user);

        mvc.perform(post("/v1/reviews/" + review.getId() + "/review-likes")
                .header("Authorization", tokenGenerator.getToken(user)))
                .andExpect(status().isOk());

        assertEquals(1, review.getReviewLikes().size());

    }

    @DisplayName("8. review 좋아요 삭제")
    @Test
    void review_like_delete() throws Exception {

        Item item = itemFactory.save(
                "한글이름",
                "englishName",
                "mainImgPath",
                15000,
                1000,
                1000,
                "좋은상품이다",
                100
        );

        Review review = reviewFactory.save(5, "정말 좋은 제품이네요. 재구매할 의향이 있습니다.", item, user);
        reviewFactory.saveReviewLike(review, user);


        mvc.perform(delete("/v1/reviews/" + review.getId() + "/review-likes")
                .header("Authorization", tokenGenerator.getToken(user)))
                .andExpect(status().isOk());


    }



}
