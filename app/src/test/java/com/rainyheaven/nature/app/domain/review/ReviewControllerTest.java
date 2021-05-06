package com.rainyheaven.nature.app.domain.review;

import com.rainyheaven.nature.app.domain.user.UserFactory;
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
public class ReviewControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ReviewFactory reviewFactory;

    @Autowired
    UserFactory userFactory;



}
