package com.rainyheaven.nature.app.domain.user;

import com.rainyheaven.nature.app.utils.TokenGenerator;
import com.rainyheaven.nature.core.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@SpringBootTest
class UserApiDocumentationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TokenGenerator tokenGenerator;

    @Autowired
    UserFactory userFactory;

    private User user;

    @BeforeEach
    public void setUp() {

        user = userFactory.createUser("test@email.com", "testname", "password1", "password1", "01012345678", "20202020");

    }

    @Test
    void testRead() throws Exception {

        mockMvc.perform(get("/v1/users").header("Authorization", tokenGenerator.getToken(user)))
                .andDo(print())
                .andDo(document("user",
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


}
