package com.rainyheaven.nature.app.domain.emailverify;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainyheaven.nature.app.domain.item.ItemFactory;
import com.rainyheaven.nature.app.domain.itemlike.ItemLikeFactory;
import com.rainyheaven.nature.app.domain.order.OrderFactory;
import com.rainyheaven.nature.app.domain.qna.QnaFactory;
import com.rainyheaven.nature.app.domain.review.ReviewFactory;
import com.rainyheaven.nature.app.domain.user.UserFactory;
import com.rainyheaven.nature.app.utils.TokenGenerator;
import com.rainyheaven.nature.core.common.dto.EmailVerifyNumConfirmRequestDto;
import com.rainyheaven.nature.core.domain.emailverify.EmailVerify;
import com.rainyheaven.nature.core.domain.user.User;
import com.rainyheaven.nature.core.exception.EmailVerifyException;
import com.rainyheaven.nature.core.exception.EmailVerifyExceptionType;
import com.rainyheaven.nature.core.exception.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EmailVerifyControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    UserFactory userFactory;

    @Autowired
    EmailVerifyFactory emailVerifyFactory;

    @Autowired
    TokenGenerator tokenGenerator;

    @Autowired
    ObjectMapper objectMapper;


    @DisplayName("1. email verify 생성")
    @Test
    void save() throws Exception {

        String email = "emailverify@email.com";

        mvc.perform(post("/v1/email-verify").param("email", email))
                .andExpect(status().isOk());

    }

    @DisplayName("2. email verify 생성시 같은 이메일로 생성한 적이 있다면 update")
    @Test
    void save_if_exist_update() throws Exception {

        String email = "emailverify@email.com";
        EmailVerify emailVerify = emailVerifyFactory.saveAcceptNone(email);
        Integer originVerifyNum = emailVerify.getVerifyNum();

        mvc.perform(post("/v1/email-verify").param("email", email))
                .andExpect(status().isOk());

        assertNotEquals(emailVerify.getVerifyNum(), originVerifyNum);


    }



    @DisplayName("3. email verify 생성 실패 - 이메일 형식에 안맞음")
    @Test
    void save_fail_email_not_valid() throws Exception {

        String email = "가나다라마바사";

        mvc.perform(post("/v1/email-verify").param("email", email))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("email")));

    }

    @DisplayName("4. email verify 생성 실패 - 이미 존재하는 이메일")
    @Test
    void save_fail_email_already_exist() throws Exception {

        String email = "test1@email.com";
        userFactory.createUser(email, "testname", "password12", "password12", "01011112222", "20200220");

        mvc.perform(post("/v1/email-verify").param("email", email))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserException))
                .andExpect(content().string(containsString("ALREADY_EXIST_EMAIL")));

    }

    @DisplayName("5. email verify confirm 성공")
    @Test
    void confirm() throws Exception {

        String email = "emailverify@email.com";
        EmailVerify emailVerify = emailVerifyFactory.saveAcceptNone(email);
        EmailVerifyNumConfirmRequestDto emailVerifyNumConfirmRequestDto = emailVerifyFactory.getEmailVerifyNumConfirmRequestDto(email, emailVerify.getVerifyNum());

        mvc.perform(patch("/v1/email-verify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emailVerifyNumConfirmRequestDto)))
                .andExpect(status().isOk());

        assertEquals(true, emailVerify.isAccepted());


    }

    @DisplayName("6. email verify confirm 실패 - 이메일 형식에 안맞음")
    @Test
    void confirm_fail_email_not_valid() throws Exception {

        String email = "emailverify@email.com";
        EmailVerify emailVerify = emailVerifyFactory.saveAcceptNone(email);

        String notValidEmail = "가나다라마바사";
        EmailVerifyNumConfirmRequestDto emailVerifyNumConfirmRequestDto = emailVerifyFactory.getEmailVerifyNumConfirmRequestDto(notValidEmail, emailVerify.getVerifyNum());

        mvc.perform(patch("/v1/email-verify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emailVerifyNumConfirmRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("email")));



    }

    @DisplayName("7. email verify confirm 실패 - verifyNum small")
    @Test
    void confirm_fail_verify_num_small() throws Exception {

        String email = "emailverify@email.com";
        emailVerifyFactory.saveAcceptNone(email);

        EmailVerifyNumConfirmRequestDto emailVerifyNumConfirmRequestDto = emailVerifyFactory.getEmailVerifyNumConfirmRequestDto(email, 123);

        mvc.perform(patch("/v1/email-verify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emailVerifyNumConfirmRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("verifyNum")));



    }

    @DisplayName("8. email verify confirm 실패 - verifyNum big")
    @Test
    void confirm_fail_verify_num_big() throws Exception {

        String email = "emailverify@email.com";
        emailVerifyFactory.saveAcceptNone(email);

        EmailVerifyNumConfirmRequestDto emailVerifyNumConfirmRequestDto = emailVerifyFactory.getEmailVerifyNumConfirmRequestDto(email, 12345);

        mvc.perform(patch("/v1/email-verify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emailVerifyNumConfirmRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("verifyNum")));



    }

    @DisplayName("9. email verify confirm 실패 - verifyNum not matched")
    @Test
    void confirm_fail_verify_num_not_matched() throws Exception {

        String email = "emailverify@email.com";
        emailVerifyFactory.saveAcceptNone(email);

        EmailVerifyNumConfirmRequestDto emailVerifyNumConfirmRequestDto = emailVerifyFactory.getEmailVerifyNumConfirmRequestDto(email, 1189);

        mvc.perform(patch("/v1/email-verify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emailVerifyNumConfirmRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmailVerifyException))
                .andExpect(content().string(containsString(EmailVerifyExceptionType.VERIFY_NUM_NOT_MATCHED.name())));

    }




}
