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
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
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


        mvc.perform(RestDocumentationRequestBuilders.get("/v1/items/{id}", item.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("nameKor")))
                .andExpect(content().string(containsString("nameEng")))
                .andExpect(content().string(containsString(item.getNameKor())))
                .andExpect(content().string(containsString(item.getNameEng())))
                .andDo(document("get_item",
                        pathParameters(
                            parameterWithName("id").description("상품 id")       
                        ),
                        responseFields(
                                fieldWithPath("id").description("상품 id"),
                                fieldWithPath("category").description("카테고리"),
                                fieldWithPath("nameKor").description("상품 한글이름"),
                                fieldWithPath("nameEng").description("상품 영어이름"),
                                fieldWithPath("description").description("상품 설명"),
                                fieldWithPath("price").description("상품 가격"),
                                fieldWithPath("discountPrice").description("상품 할인금액"),
                                fieldWithPath("capacity").description("상품 용량"),
                                fieldWithPath("savePoints").description("적립될 포인트"),
                                fieldWithPath("mainImgPath").description("상품 메인 이미지 경로"),
                                fieldWithPath("detailImgPath").description("상품 상세 이미지 경로")
                        )
                ));


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

        mvc.perform(get("/v1/items/")
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(12))
                .param("sort", "createdDate,DESC"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get_item_pages",
                        requestParameters(
                                parameterWithName("page").description("페이지"),
                                parameterWithName("size").description("사이즈"),
                                parameterWithName("sort").description("정렬")
                        ),
                        responseFields(
                                fieldWithPath("content").description("content"),
                                fieldWithPath("content.[].id").description("상품 id"),
                                fieldWithPath("content.[].nameKor").description("상품 한글 이름"),
                                fieldWithPath("content.[].nameEng").description("상품 영어 이름"),
                                fieldWithPath("content.[].price").description("상품 가격"),
                                fieldWithPath("content.[].discountPrice").description("상품 할인 금액"),
                                fieldWithPath("content.[].mainImgPath").description("상품 메인 이미지 경로"),
                                fieldWithPath("content.[].description").description("상품 상세 이미지 경로"),
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
                .param("keyword", item.getNameKor())
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(12))
                .param("sort", "sellTotal,DESC"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(item.getNameKor())))
                .andDo(document("item_search",
                        requestParameters(
                                parameterWithName("keyword").description("검색어"),
                                parameterWithName("page").description("페이지"),
                                parameterWithName("size").description("사이즈"),
                                parameterWithName("sort").description("정렬")
                        ),
                        responseFields(
                                fieldWithPath("content").description("content"),
                                fieldWithPath("content.[].id").description("상품 id"),
                                fieldWithPath("content.[].nameKor").description("상품 한글 이름"),
                                fieldWithPath("content.[].nameEng").description("상품 영어 이름"),
                                fieldWithPath("content.[].price").description("상품 가격"),
                                fieldWithPath("content.[].discountPrice").description("상품 할인 금액"),
                                fieldWithPath("content.[].mainImgPath").description("상품 메인 이미지 경로"),
                                fieldWithPath("content.[].description").description("상품 상세 이미지 경로"),
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



        mvc.perform(RestDocumentationRequestBuilders.post("/v1/items/{id}/item-likes", item.getId())
                .header("Authorization", tokenGenerator.getToken(user)))
                .andExpect(status().isOk())
                .andDo(document("item_like",
                        requestHeaders(
                            headerWithName("Authorization").description("유저 token")
                        ),
                        pathParameters(
                                parameterWithName("id").description("상품 id")
                        )
                ));


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

        mvc.perform(RestDocumentationRequestBuilders.delete("/v1/items/{id}/item-likes", item.getId())
                .header("Authorization", tokenGenerator.getToken(user)))
                .andExpect(status().isOk())
                .andDo(document("item_like_cancel",
                        requestHeaders(
                                headerWithName("Authorization").description("유저 token")
                        ),
                        pathParameters(
                                parameterWithName("id").description("상품 id")
                        )
                ));

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


        mvc.perform(RestDocumentationRequestBuilders.get("/v1/items/{id}/qnas", item.getId())
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(5))
                .param("sort", "createdDate,DESC")
                .header("Authorization", tokenGenerator.getToken(user)))
                .andExpect(status().isOk())
                .andDo(document("get_item_qna", 
                        requestHeaders(
                            headerWithName("Authorization").description("유저 token")
                        ),
                        pathParameters(
                                parameterWithName("id").description("상품 id")
                        ),
                        requestParameters(
                                parameterWithName("page").description("페이지"),
                                parameterWithName("size").description("사이즈"),
                                parameterWithName("sort").description("정렬")
                        ),
                        responseFields(
                                fieldWithPath("content").description("content"),
                                fieldWithPath("content.[].id").description("qna id"),
                                fieldWithPath("content.[].writer").description("작성자"),
                                fieldWithPath("content.[].content").description("작성 내용"),
                                fieldWithPath("content.[].isSecret").description("비밀글 여부"),
                                fieldWithPath("content.[].isOwn").description("토큰 유저의 게시글인지 여부"),
                                fieldWithPath("content.[].status").description("답변 여부"),
                                fieldWithPath("content.[].wroteAt").description("작성일"),
                                fieldWithPath("content.[].itemResponseDto").description("상품 dto"),
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


        mvc.perform(RestDocumentationRequestBuilders.post("/v1/items/{id}/qnas", item.getId())
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(qnaSaveRequestDto)))
                .andExpect(status().isOk())
                .andDo(document("item_qna_create",
                        requestHeaders(
                                headerWithName("Authorization").description("유저 token")
                        ),
                        pathParameters(
                                parameterWithName("id").description("상품 id")
                        ),
                        requestFields(
                                fieldWithPath("content").description("문의 내용"),
                                fieldWithPath("secret").description("비밀글 여부")
                        )
                ));

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

        mvc.perform(RestDocumentationRequestBuilders.get("/v1/items/{id}/reviews", item.getId())
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(5))
                .param("sort", "createdDate,DESC"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(review.getContent())))
                .andDo(document("get_item_reviews",
                        pathParameters(
                                parameterWithName("id").description("상품 id")
                        ),
                        requestParameters(
                                parameterWithName("page").description("페이지"),
                                parameterWithName("size").description("사이즈"),
                                parameterWithName("sort").description("정렬")
                        ),
                        responseFields(
                                fieldWithPath("content").description("content"),
                                fieldWithPath("content.[].id").description("리뷰 id"),
                                fieldWithPath("content.[].content").description("리뷰 내용"),
                                fieldWithPath("content.[].likesCount").description("리뷰 좋아요 개수"),
                                fieldWithPath("content.[].rating").description("평점"),
                                fieldWithPath("content.[].wroteAt").description("작성일"),
                                fieldWithPath("content.[].itemResponseDto").description("상품 dto"),
                                fieldWithPath("content.[].itemResponseDto.id").description("상품 id"),
                                fieldWithPath("content.[].itemResponseDto.nameKor").description("상품 한글 이름"),
                                fieldWithPath("content.[].itemResponseDto.nameEng").description("상품 영어 이름"),
                                fieldWithPath("content.[].itemResponseDto.price").description("상품 가격"),
                                fieldWithPath("content.[].itemResponseDto.discountPrice").description("상품 할인 금액"),
                                fieldWithPath("content.[].itemResponseDto.mainImgPath").description("상품 메인 이미지 경로"),
                                fieldWithPath("content.[].itemResponseDto.description").description("상품 설명"),
                                fieldWithPath("content.[].reviewImageResponseDtos").description("리뷰 이미지 dtos"),
                                fieldWithPath("content.[].writer").description("리뷰 작성자"),
                                fieldWithPath("content.[].userLike").description("토큰 유저가 좋아요를 했는지 여부"),
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

        mvc.perform(RestDocumentationRequestBuilders.get("/v1/items/{id}/count/qnas", item.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("2")))
                .andDo(document("get_item_qna_total",
                   pathParameters(
                           parameterWithName("id").description("상품 id")
                   )
                ));



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

        mvc.perform(RestDocumentationRequestBuilders.get("/v1/items/{id}/count/reviews", item.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("2")))
                .andDo(document("get_item_reviews_total",
                   pathParameters(
                           parameterWithName("id").description("상품 id")
                   )
                ));


    }







}
