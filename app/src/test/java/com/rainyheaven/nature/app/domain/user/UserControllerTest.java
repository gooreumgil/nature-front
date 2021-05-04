package com.rainyheaven.nature.app.domain.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainyheaven.nature.app.domain.emailverify.EmailVerifyFactory;
import com.rainyheaven.nature.app.domain.item.ItemFactory;
import com.rainyheaven.nature.app.domain.order.OrderFactory;
import com.rainyheaven.nature.app.utils.TokenGenerator;
import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.item.ItemRepository;
import com.rainyheaven.nature.core.domain.order.Order;
import com.rainyheaven.nature.core.domain.user.User;
import com.rainyheaven.nature.core.domain.user.dto.app.UserSaveRequestDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    WebApplicationContext ctx;

    @Autowired
    UserFactory userFactory;

    @Autowired
    EmailVerifyFactory emailVerifyFactory;

    @Autowired
    ItemFactory itemFactory;

    @Autowired
    OrderFactory orderFactory;

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

    @DisplayName("1. 유저 조회 성공")
    @Test
    void get_user() throws Exception {

        String token = tokenGenerator.getToken(user);

        mvc.perform(get("/v1/users").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(content().string(containsString("name")))
                .andExpect(content().string(containsString("phoneNum1")))
                .andExpect(content().string(containsString("phoneNum2")))
                .andExpect(content().string(containsString("phoneNum3")));

    }

    @DisplayName("2. 유저 조회 실패 - 토큰 없음")
    @Test
    void get_user_fail_token_null() throws Exception {

        mvc.perform(get("/v1/users").header("Authorization", ""))
                .andExpect(status().isForbidden());

    }
    
    @DisplayName("3. 회원가입 성공")
    @Test
    void register() throws Exception {

        UserSaveRequestDto userSaveRequestDto = userFactory.getUserSaveRequestDto(
                "success@email.com",
                "success",
                "password12",
                "password12",
                "01011112222",
                "20200220");
        emailVerifyFactory.save(userSaveRequestDto.getEmail());

        mvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSaveRequestDto)))
                .andExpect(status().isOk());

    }

    @DisplayName("4. 회원가입 실패 - 이메일 인증 안함")
    @Test
    void register_fail_email_verify_none() throws Exception {

        UserSaveRequestDto userSaveRequestDto = userFactory.getUserSaveRequestDto(
                "test1@email.com",
                "testname",
                "password12",
                "password12",
                "01011112222",
                "20200220");


        mvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect((result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("email")));

    }

    @DisplayName("5. 회원가입 실패 - 이메일 없음")
    @Test
    void register_fail_email_null() throws Exception {

        UserSaveRequestDto userSaveRequestDto = userFactory.getUserSaveRequestDto(
                null,
                "testname",
                "password12",
                "password12",
                "01011112222",
                "20200220");;

        mvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect((result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("email")));

    }

    @DisplayName("6. 회원가입 실패 - 이메일 형식이 아님")
    @Test
    void register_fail_email_form_error() throws Exception {

        UserSaveRequestDto userSaveRequestDto = userFactory.getUserSaveRequestDto(
                "test1@email.com",
                "testname",
                "password12",
                "password12",
                "01011112222",
                "20200220");

        emailVerifyFactory.save(userSaveRequestDto.getEmail());

        mvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect((result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("email")));

    }


    @DisplayName("7. 회원가입 실패 - 이메일 길이 안 맞음")
    @Test
    void register_fail_email_length_not_matched() throws Exception {

        UserSaveRequestDto userSaveRequestDto = userFactory.getUserSaveRequestDto(
                "email12345677829394923939429394@email.com",
                "testname",
                "password12",
                "password12",
                "01011112222",
                "20200220");

        emailVerifyFactory.save(userSaveRequestDto.getEmail());

        mvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect((result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("email")));

    }

    @DisplayName("8. 회원가입 실패 - 유저네임 null")
    @Test
    void register_fail_name_null() throws Exception {

        UserSaveRequestDto userSaveRequestDto = userFactory.getUserSaveRequestDto(
                "test1@email.com",
                null,
                "password12",
                "password12",
                "01011112222",
                "20200220");

        emailVerifyFactory.save(userSaveRequestDto.getEmail());

        mvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect((result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("name")));

    }

    @DisplayName("8. 회원가입 실패 - 유저네임 길이 long")
    @Test
    void register_fail_name_length_long() throws Exception {

        UserSaveRequestDto userSaveRequestDto = userFactory.getUserSaveRequestDto(
                "test1@email.com",
                "longnamelongnamelongname",
                "password12",
                "password12",
                "01011112222",
                "20200220");

        emailVerifyFactory.save(userSaveRequestDto.getEmail());

        mvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect((result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("name")));

    }

    @DisplayName("9. 회원가입 실패 - 유저네임 길이 short")
    @Test
    void register_fail_name_length_short() throws Exception {

        UserSaveRequestDto userSaveRequestDto = userFactory.getUserSaveRequestDto(
                "test1@email.com",
                "t",
                "testpassword",
                "testpassword",
                "01011112222",
                "20200220");

        emailVerifyFactory.save(userSaveRequestDto.getEmail());

        mvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect((result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("name")));

    }

    @DisplayName("10. 회원가입 실패 - 패스워드 null")
    @Test
    void register_fail_password_null() throws Exception {

        UserSaveRequestDto userSaveRequestDto = userFactory.getUserSaveRequestDto(
                "test1@email.com",
                "testname",
                null,
                "testpassword",
                "01011112222",
                "20200220");

        emailVerifyFactory.save(userSaveRequestDto.getEmail());

        mvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect((result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("password")));

    }

    @DisplayName("11. 회원가입 실패 - 패스워드 long")
    @Test
    void register_fail_password_long() throws Exception {

        UserSaveRequestDto userSaveRequestDto = userFactory.getUserSaveRequestDto(
                "test1@email.com",
                "testname",
                "longpasswordlongpasswordlongpassword",
                "longpasswordlongpasswordlongpassword",
                "01011112222",
                "20200220");

        emailVerifyFactory.save(userSaveRequestDto.getEmail());

        mvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect((result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("password")));

    }

    @DisplayName("12. 회원가입 실패 - 패스워드 short")
    @Test
    void register_fail_password_short() throws Exception {

        UserSaveRequestDto userSaveRequestDto = userFactory.getUserSaveRequestDto(
                "test1@email.com",
                "testname",
                "short",
                "short",
                "01011112222",
                "20200220");

        emailVerifyFactory.save(userSaveRequestDto.getEmail());

        mvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect((result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("password")));

    }

    @DisplayName("13. 회원가입 실패 - 패스워드 서로 안 맞음")
    @Test
    void register_fail_password_not_matched() throws Exception {

        UserSaveRequestDto userSaveRequestDto = userFactory.getUserSaveRequestDto(
                "test1@email.com",
                "testname",
                "password12",
                "password123",
                "01011112222",
                "20200220");

        emailVerifyFactory.save(userSaveRequestDto.getEmail());

        mvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect((result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("passwordMatchedValidator")));

    }

    @DisplayName("14. 회원가입 실패 - phoneNumber null")
    @Test
    void register_fail_phone_number_null() throws Exception {

        UserSaveRequestDto userSaveRequestDto = userFactory.getUserSaveRequestDto(
                "test1@email.com",
                "testname",
                "password12",
                "password12",
                null,
                "20200220");

        emailVerifyFactory.save(userSaveRequestDto.getEmail());

        mvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect((result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("phoneNumber")));

    }

    @DisplayName("15. 회원가입 실패 - 유효하지 않은 phoneNumber")
    @Test
    void register_fail_phone_number_not_valid() throws Exception {

        UserSaveRequestDto userSaveRequestDto = userFactory.getUserSaveRequestDto(
                "test1@email.com",
                "testname",
                "password12",
                "password12",
                "가나다라마바사",
                "20200220");

        emailVerifyFactory.save(userSaveRequestDto.getEmail());

        mvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect((result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("phoneNumber")));

    }

    @DisplayName("15. 회원가입 실패 - birthDay null")
    @Test
    void register_fail_birth_day_null() throws Exception {

        UserSaveRequestDto userSaveRequestDto = userFactory.getUserSaveRequestDto(
                "test1@email.com",
                "testname",
                "password12",
                "password12",
                "01011112222",
                null);

        emailVerifyFactory.save(userSaveRequestDto.getEmail());

        mvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect((result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("birthDay")));

    }

    @DisplayName("16. 회원가입 실패 - birthDay long")
    @Test
    void register_fail_birth_day_long() throws Exception {

        UserSaveRequestDto userSaveRequestDto = userFactory.getUserSaveRequestDto(
                "test1@email.com",
                "testname",
                "password12",
                "password12",
                "01011112222",
                "2020112233");

        emailVerifyFactory.save(userSaveRequestDto.getEmail());

        mvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect((result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("birthDay")));

    }

    @DisplayName("17. 회원가입 실패 - 유효하지 않은 birthDay")
    @Test
    void register_fail_birth_day_not_valid() throws Exception {

        UserSaveRequestDto userSaveRequestDto = userFactory.getUserSaveRequestDto(
                "test1@email.com",
                "testname",
                "password12",
                "password12",
                "01011112222",
                "가나다라마바사아");

        emailVerifyFactory.save(userSaveRequestDto.getEmail());

        mvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect((result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("birthDay")));

    }

    @DisplayName("18. 회원가입 실패 - 이미 존재하는 이메일")
    @Test
    void register_fail_email_duplicated() throws Exception {

        UserSaveRequestDto userSaveRequestDto = userFactory.getUserSaveRequestDto(
                "test1@email.com",
                "testname",
                "password12",
                "password12",
                "01011112222",
                "가나다라마바사아");

        emailVerifyFactory.save(userSaveRequestDto.getEmail());

        mvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect((result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("email")));

    }




    @DisplayName("19. order 내역 불러오기 성공")
    @Test
    void get_order_pages() throws Exception {


        List<Item> items = itemFactory.listInit();
        orderFactory.save(user, items);

        mvc.perform(get("/v1/users/orders").header("Authorization", tokenGenerator.getToken(user)))
                .andExpect(status().isOk());

        Page<Order> orderPage = orderFactory.findByUser(user);
        assertEquals(orderPage.getTotalElements(), 1);


    }

    @DisplayName("20. deliveryStatus로 order 불러오기")
    @Test
    void get_total_by_delivery_status() throws Exception {

        List<Item> items = itemFactory.listInit();
        orderFactory.save(user, items);

        mvc.perform(get("/v1/users/count/orders/READY").header("Authorization", tokenGenerator.getToken(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));

    }

    @DisplayName("21.리뷰 불러오기")
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