package com.rainyheaven.nature.app.domain.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    UserFactory userFactory;

    @Test
    void get() {


    }

    @Test
    void register() {
    }

    @Test
    void getOrderPages() {
    }

    @Test
    void getTotalByDeliveryStatus() {
    }

    @Test
    void getReviews() {
    }

    @Test
    void passwordChangeLinkSend() {
    }

    @Test
    void passwordChangeByEmail() {
    }

    @Test
    void getTotalReviews() {
    }

    @Test
    void getOrderItems() {
    }

    @Test
    void getLikeItems() {
    }

    @Test
    void checkHasItemLike() {
    }

    @Test
    void getQnaPage() {
    }
}