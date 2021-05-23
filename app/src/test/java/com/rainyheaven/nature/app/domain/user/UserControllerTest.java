package com.rainyheaven.nature.app.domain.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainyheaven.nature.app.domain.emailverify.EmailVerifyFactory;
import com.rainyheaven.nature.app.domain.item.ItemFactory;
import com.rainyheaven.nature.app.domain.itemlike.ItemLikeFactory;
import com.rainyheaven.nature.app.domain.order.OrderFactory;
import com.rainyheaven.nature.app.domain.qna.QnaFactory;
import com.rainyheaven.nature.app.domain.review.ReviewFactory;
import com.rainyheaven.nature.app.utils.TokenGenerator;
import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.order.Order;
import com.rainyheaven.nature.core.domain.user.User;
import com.rainyheaven.nature.core.domain.user.dto.app.PasswordChangeRequestDto;
import com.rainyheaven.nature.core.domain.user.dto.app.UserSaveRequestDto;
import com.rainyheaven.nature.core.exception.UserException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
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
    ReviewFactory reviewFactory;

    @Autowired
    ItemLikeFactory itemLikeFactory;

    @Autowired
    QnaFactory qnaFactory;

    @Autowired
    TokenGenerator tokenGenerator;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

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
                .andExpect(content().string(containsString("phoneNum3")))
                .andDo(document("get_user",
                        responseFields(
                                fieldWithPath("id").description("id"),
                                fieldWithPath("name").description("닉네임"),
                                fieldWithPath("phoneNum1").description("핸드폰 번호 앞자리"),
                                fieldWithPath("phoneNum2").description("핸드폰 번호 중간"),
                                fieldWithPath("phoneNum3").description("핸드폰 번호 마지막"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("ownPoints").description("보유 포인트"),
                                fieldWithPath("addressResponseDtos").description("주소목록")
                        )
                ));

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
                .andExpect(status().isOk())
                .andDo(document("register_user",
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("name").description("닉네임"),
                                fieldWithPath("password").description("비밀번호"),
                                fieldWithPath("passwordConfirm").description("비밀번호 확인"),
                                fieldWithPath("phoneNumber").description("핸드폰 번호"),
                                fieldWithPath("birthDay").description("생년월일")
                        )
                ));

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
                .andExpect(content().string(containsString("passwordMatched")));

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

        mvc.perform(get("/v1/users/orders")
                .header("Authorization", tokenGenerator.getToken(user))
                .param("page", "0")
                .param("size", "5")
                .param("sort", "createdDate"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get_order_pages",
                        requestParameters(
                                parameterWithName("page").description("페이지"),
                                parameterWithName("size").description("사이즈"),
                                parameterWithName("sort").description("정렬")
                        ),
                        responseFields(
                                fieldWithPath("content").description("content"),
                                fieldWithPath("content.[].id").description("id"),
                                fieldWithPath("content.[].finalDiscountPrice").description("할인 금액 합산"),
                                fieldWithPath("content.[].finalPrice").description("최종 가격"),
                                fieldWithPath("content.[].usedPoints").description("사용한 포인트"),
                                fieldWithPath("content.[].savedPoints").description("적립된 포인트"),
                                fieldWithPath("content.[].status").description("주문 상태"),
                                fieldWithPath("content.[].orderAt").description("주문 날짜"),
                                fieldWithPath("content.[].deliveryResponseDto.id").description("배송 id"),
                                fieldWithPath("content.[].deliveryResponseDto.receiver").description("배송 받는 사람 이름"),
                                fieldWithPath("content.[].deliveryResponseDto.deliveryPrice").description("배송비"),
                                fieldWithPath("content.[].deliveryResponseDto.memo").description("배송 메모"),
                                fieldWithPath("content.[].deliveryResponseDto.phoneNum1").description("핸드폰 번호 앞자리"),
                                fieldWithPath("content.[].deliveryResponseDto.phoneNum2").description("핸드폰 번호 중간"),
                                fieldWithPath("content.[].deliveryResponseDto.phoneNum3").description("핸드폰 번호 마지막자리"),
                                fieldWithPath("content.[].deliveryResponseDto.mainAddress").description("메인 주소"),
                                fieldWithPath("content.[].deliveryResponseDto.detailAddress").description("상세 주소"),
                                fieldWithPath("content.[].deliveryResponseDto.zipCode").description("우편번호"),
                                fieldWithPath("content.[].deliveryResponseDto.status").description("배송 상태"),
                                fieldWithPath("content.[].orderItemResponseDtos.[].id").description("주문 상품 id"),
                                fieldWithPath("content.[].orderItemResponseDtos.[].itemId").description("상품 id"),
                                fieldWithPath("content.[].orderItemResponseDtos.[].itemNameKor").description("상품 한글 이름"),
                                fieldWithPath("content.[].orderItemResponseDtos.[].itemNameEng").description("상품 영어 이름"),
                                fieldWithPath("content.[].orderItemResponseDtos.[].itemPrice").description("상품 가격"),
                                fieldWithPath("content.[].orderItemResponseDtos.[].itemDiscountPrice").description("상품 할인 금액"),
                                fieldWithPath("content.[].orderItemResponseDtos.[].itemQuantity").description("구매한 상품 수량"),
                                fieldWithPath("content.[].orderItemResponseDtos.[].mainImgPath").description("상품 이미지 path"),
                                fieldWithPath("content.[].orderItemResponseDtos.[].orderedAt").description("주문 날짜"),
                                fieldWithPath("content.[].orderItemResponseDtos.[].isLeaveReview").description("리뷰를 남겼는지 여부"),
                                fieldWithPath("pageable").description("페이징"),
                                fieldWithPath("pageable.sort").description("페이징 정렬"),
                                fieldWithPath("pageable.sort.unsorted").description("unsorted"),
                                fieldWithPath("pageable.sort.sorted").description("sorted"),
                                fieldWithPath("pageable.sort.empty").description("empty"),
                                fieldWithPath("pageable.offset").description("offset"),
                                fieldWithPath("pageable.pageSize").description("pageSize"),
                                fieldWithPath("pageable.pageNumber").description("pageNumber"),
                                fieldWithPath("pageable.paged").description("paged"),
                                fieldWithPath("pageable.unpaged").description("unpaged"),
                                fieldWithPath("totalElements").description("총 order 개수"),
                                fieldWithPath("totalPages").description("전체 페이지"),
                                fieldWithPath("number").description("number"),
                                fieldWithPath("size").description("size"),
                                fieldWithPath("last").description("last"),
                                fieldWithPath("sort").description("sort"),
                                fieldWithPath("sort.unsorted").description("unsorted"),
                                fieldWithPath("sort.sorted").description("sorted"),
                                fieldWithPath("sort.empty").description("empty"),
                                fieldWithPath("first").description("first"),
                                fieldWithPath("numberOfElements").description("numberOfElements"),
                                fieldWithPath("empty").description("empty")

                        )

                ));

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
    void get_reviews() throws Exception {

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

        reviewFactory.save(5, "써보니까 좋네요", item, user);

        mvc.perform(get("/v1/users/reviews").header("Authorization", tokenGenerator.getToken(user)))
                .andExpect(status().isOk());


    }

    @DisplayName("22. 패스워드 변경 링크 전송 성공")
    @Test
    void password_change_link_send() throws Exception {

        mvc.perform(post("/v1/users/" + "test1@email.com" + "/password/change-link-send"))
                .andExpect(status().isOk());

    }

    @DisplayName("23. 패스워드 변경 링크 전송 실패 - 존재하지 않는 이메일")
    @Test
    void password_change_link_send_fail_not_exist_user() throws Exception {

        mvc.perform(post("/v1/users/" + "unknown@email.com" + "/password/change-link-send"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserException));

    }

    @DisplayName("23. 패스워드 변경 링크 전송 실패 - email null")
    @Test
    void password_change_link_send_fail_email_null() throws Exception {

        mvc.perform(post("/v1/users/" + null + "/password/change-link-send"))
                .andExpect(status().isBadRequest());

    }

    @DisplayName("25. 패스워드 변경 성공")
    @Test
    void password_change_by_email() throws Exception {

        PasswordChangeRequestDto passwordChangeRequestDto = new PasswordChangeRequestDto("testpassword", "testpassword");


        mvc.perform(patch("/v1/users/" + user.getEmail() + "/password/change-by-email")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(passwordChangeRequestDto)))
                .andExpect(status().isOk());

        assertNotEquals(passwordEncoder.encode(passwordChangeRequestDto.getPassword()), user.getPassword());

    }

    @DisplayName("26. 패스워드 변경 실패 - 존재하지 않는 이메일")
    @Test
    void password_change_by_email_fail_not_exist_email() throws Exception {

        PasswordChangeRequestDto passwordChangeRequestDto = new PasswordChangeRequestDto("testpassword", "testpassword");
        String email = "unknown@email.com";

        mvc.perform(patch("/v1/users/" + email + "/password/change-by-email")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(passwordChangeRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("email")))
                .andDo(print());


    }

    @DisplayName("27. 리뷰 총 개수 구하기")
    @Test
    void get_total_reviews() throws Exception {

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

        reviewFactory.save(5, "써보니까 좋네요", item, user);

        mvc.perform(get("/v1/users/count/reviews").header("Authorization", tokenGenerator.getToken(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));


    }

    @DisplayName("28. orderItems orderComp 조회")
    @Test
    void get_order_items() throws Exception {

        List<Item> items = itemFactory.listInit();
        Order order = orderFactory.save(user, items);
        orderFactory.comp(order);

        mvc.perform(get("/v1/users/order-items").header("Authorization", tokenGenerator.getToken(user)))
                .andExpect(status().isOk());

    }

    @DisplayName("29. 아이템 좋아요 가져오기")
    @Test
    void get_like_items() throws Exception {

        Item item = itemFactory.save(
                "한글이름",
                "englishName",
                "mainImgPath",
                10000,
                500,
                1000,
                "제품설명",
                50);

        itemLikeFactory.save(item, user);

        mvc.perform(get("/v1/users/item-likes").header("Authorization", tokenGenerator.getToken(user)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(item.getNameKor())))
                .andExpect(content().string(containsString(item.getNameEng())))
                .andExpect(content().string(containsString(String.valueOf(item.getPrice()))));


    }

    @DisplayName("30. 해당 상품을 좋아요 했는지 체크")
    @Test
    void check_has_item_like() throws Exception {

        Item item = itemFactory.save(
                "한글이름",
                "englishName",
                "mainImgPath",
                10000,
                500,
                1000,
                "제품설명",
                50);


        itemLikeFactory.save(item, user);

        mvc.perform(get("/v1/users/check/item-likes")
                .param("itemId", String.valueOf(item.getId()))
                .header("Authorization", tokenGenerator.getToken(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

    }

    @DisplayName("31. 해당 상품을 좋아요 했는지 체크 - 좋아요 하지 않은 상품")
    @Test
    void check_has_item_like_not_exist() throws Exception {

        Item item = itemFactory.save(
                "한글이름",
                "englishName",
                "mainImgPath",
                10000,
                500,
                1000,
                "제품설명",
                50);

        mvc.perform(get("/v1/users/check/item-likes")
                .param("itemId", String.valueOf(item.getId()))
                .header("Authorization", tokenGenerator.getToken(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

    }

    @DisplayName("32. Qna 불러오기")
    @Test
    void get_qna_page() throws Exception {

        Item item = itemFactory.save(
                "한글이름",
                "englishName",
                "mainImgPath",
                10000,
                500,
                1000,
                "제품설명",
                50);

        qnaFactory.save(user, item, "문의내용입니다.", true);

        mvc.perform(get("/v1/users/qnas").header("Authorization", tokenGenerator.getToken(user)))
                .andExpect(status().isOk())
                .andDo(print());



        
    }

}