package com.rainyheaven.nature.app.domain.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainyheaven.nature.app.domain.item.ItemFactory;
import com.rainyheaven.nature.app.domain.user.UserFactory;
import com.rainyheaven.nature.app.utils.TokenGenerator;
import com.rainyheaven.nature.core.domain.delivery.DeliveryStatus;
import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.order.Order;
import com.rainyheaven.nature.core.domain.order.OrderStatus;
import com.rainyheaven.nature.core.domain.order.dto.app.OrderSaveRequestDto;
import com.rainyheaven.nature.core.domain.user.User;
import com.rainyheaven.nature.core.exception.DomainException;
import com.rainyheaven.nature.core.exception.OrderException;
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
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    OrderFactory orderFactory;

    @Autowired
    UserFactory userFactory;

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

    @DisplayName("1. order 불러오기")
    @Test
    void get_order() throws Exception {

        List<Item> items = itemFactory.listInit();
        Order order = orderFactory.save(user, items);

        mvc.perform(get("/v1/orders/" + order.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(order.getId()))));

    }

    @DisplayName("2. order 불러오기 실패 - not exist")
    @Test
    void get_order_fail_not_exist() throws Exception {

        mvc.perform(get("/v1/orders/" + 404L))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderException))
                .andExpect(content().string(containsString("NOT_EXIST_ORDER")));

    }

    @DisplayName("3. order 생성 성공")
    @Test
    void save() throws Exception {
        List<Item> items = itemFactory.listInit();
        OrderSaveRequestDto orderSaveRequestDto = orderFactory.getOrderSaveRequestDto(user, items);

        mvc.perform(post("/v1/orders")
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderSaveRequestDto)))
                .andExpect(status().isOk());

    }

    @DisplayName("4. order 생성 실패 - 받는사람 null")
    @Test
    void save_fail_receiver_null() throws Exception {
        List<Item> items = itemFactory.listInit();
        OrderSaveRequestDto orderSaveRequestDto = orderFactory.getOrderSaveRequestDto(user, items);
        orderSaveRequestDto.setReceiver(null);

        mvc.perform(post("/v1/orders")
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("receiver")));

    }

    @DisplayName("5. order 생성 실패 - 받는사람 long")
    @Test
    void save_fail_receiver_long() throws Exception {
        List<Item> items = itemFactory.listInit();
        OrderSaveRequestDto orderSaveRequestDto = orderFactory.getOrderSaveRequestDto(user, items);
        orderSaveRequestDto.setReceiver("longReceiverlongReceiverlongReceiverlongReceiverlongReceiverlongReceiverlongReceiverlongReceiverlongReceiverlongReceiverlongReceiverlongReceiver");

        mvc.perform(post("/v1/orders")
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("receiver")));

    }

    @DisplayName("6. order 생성 실패 - phoneNumber null")
    @Test
    void save_fail_phone_number_null() throws Exception {
        List<Item> items = itemFactory.listInit();
        OrderSaveRequestDto orderSaveRequestDto = orderFactory.getOrderSaveRequestDto(user, items);
        orderSaveRequestDto.setPhoneNum1(null);

        mvc.perform(post("/v1/orders")
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("phoneNumberValid")));

    }

    @DisplayName("7. order 생성 실패 - phoneNumber 형식에 안 맞음")
    @Test
    void save_fail_phone_number_not_valid() throws Exception {
        List<Item> items = itemFactory.listInit();
        OrderSaveRequestDto orderSaveRequestDto = orderFactory.getOrderSaveRequestDto(user, items);
        orderSaveRequestDto.setPhoneNum1("가나다");
        orderSaveRequestDto.setPhoneNum2("라마바");
        orderSaveRequestDto.setPhoneNum3("사아자카타");

        mvc.perform(post("/v1/orders")
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("phoneNumberValid")));

    }

    @DisplayName("8. order 생성 실패 - phoneNumber 형식에 안 맞음2")
    @Test
    void save_fail_phone_number_not_valid2() throws Exception {
        List<Item> items = itemFactory.listInit();
        OrderSaveRequestDto orderSaveRequestDto = orderFactory.getOrderSaveRequestDto(user, items);
        orderSaveRequestDto.setPhoneNum1("010");
        orderSaveRequestDto.setPhoneNum2("1111");
        orderSaveRequestDto.setPhoneNum3("22222");

        mvc.perform(post("/v1/orders")
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("phoneNumberValid")));

    }

    @DisplayName("9. order 생성 실패 - finalPrice null")
    @Test
    void save_fail_final_price_null() throws Exception {
        List<Item> items = itemFactory.listInit();
        OrderSaveRequestDto orderSaveRequestDto = orderFactory.getOrderSaveRequestDto(user, items);
        orderSaveRequestDto.setFinalPrice(null);

        mvc.perform(post("/v1/orders")
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("finalPrice")));

    }

    @DisplayName("10. order 생성 실패 - mainAddress null")
    @Test
    void save_fail_main_address_null() throws Exception {
        List<Item> items = itemFactory.listInit();
        OrderSaveRequestDto orderSaveRequestDto = orderFactory.getOrderSaveRequestDto(user, items);
        orderSaveRequestDto.getAddressRequestDto().setMainAddress(null);

        mvc.perform(post("/v1/orders")
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("mainAddress")));

    }

    @DisplayName("11. order 생성 실패 - mainAddress long")
    @Test
    void save_fail_main_address_long() throws Exception {
        List<Item> items = itemFactory.listInit();
        OrderSaveRequestDto orderSaveRequestDto = orderFactory.getOrderSaveRequestDto(user, items);
        orderSaveRequestDto.getAddressRequestDto().setMainAddress("longMainAddresslongMainAddresslongMainAddresslongMainAddresslongMainAddresslongMainAddresslongMainAddresslongMainAddresslongMainAddresslongMainAddresslongMainAddresslongMainAddresslongMainAddress");

        mvc.perform(post("/v1/orders")
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("mainAddress")));

    }

    @DisplayName("12. order 생성 실패 - mainAddress short")
    @Test
    void save_fail_main_address_short() throws Exception {
        List<Item> items = itemFactory.listInit();
        OrderSaveRequestDto orderSaveRequestDto = orderFactory.getOrderSaveRequestDto(user, items);
        orderSaveRequestDto.getAddressRequestDto().setMainAddress("t");

        mvc.perform(post("/v1/orders")
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("mainAddress")));

    }

    @DisplayName("13. order 생성 실패 - detailAddress null")
    @Test
    void save_fail_detail_address_null() throws Exception {
        List<Item> items = itemFactory.listInit();
        OrderSaveRequestDto orderSaveRequestDto = orderFactory.getOrderSaveRequestDto(user, items);
        orderSaveRequestDto.getAddressRequestDto().setDetailAddress(null);

        mvc.perform(post("/v1/orders")
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("detailAddress")));

    }

    @DisplayName("14. order 생성 실패 - detailAddress long")
    @Test
    void save_fail_detail_address_long() throws Exception {
        List<Item> items = itemFactory.listInit();
        OrderSaveRequestDto orderSaveRequestDto = orderFactory.getOrderSaveRequestDto(user, items);
        orderSaveRequestDto.getAddressRequestDto().setDetailAddress("longDetailAddresslongDetailAddresslongDetailAddresslongDetailAddresslongDetailAddresslongDetailAddresslongDetailAddresslongDetailAddresslongDetailAddresslongDetailAddresslongDetailAddresslongDetailAddress");

        mvc.perform(post("/v1/orders")
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("detailAddress")));

    }

    @DisplayName("15. order 생성 실패 - detailAddress short")
    @Test
    void save_fail_detail_address_short() throws Exception {
        List<Item> items = itemFactory.listInit();
        OrderSaveRequestDto orderSaveRequestDto = orderFactory.getOrderSaveRequestDto(user, items);
        orderSaveRequestDto.getAddressRequestDto().setDetailAddress("t");

        mvc.perform(post("/v1/orders")
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("detailAddress")));

    }

    @DisplayName("16. order 생성 실패 - zipCode null")
    @Test
    void save_fail_zip_code_null() throws Exception {
        List<Item> items = itemFactory.listInit();
        OrderSaveRequestDto orderSaveRequestDto = orderFactory.getOrderSaveRequestDto(user, items);
        orderSaveRequestDto.getAddressRequestDto().setZipCode(null);

        mvc.perform(post("/v1/orders")
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("zipCode")));

    }

    @DisplayName("17. order 생성 실패 - zipCode 형식 안 맞음")
    @Test
    void save_fail_zip_code_not_valid() throws Exception {
        List<Item> items = itemFactory.listInit();
        OrderSaveRequestDto orderSaveRequestDto = orderFactory.getOrderSaveRequestDto(user, items);
        orderSaveRequestDto.getAddressRequestDto().setZipCode("가나다라마바사");

        mvc.perform(post("/v1/orders")
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("zipCode")))
                .andDo(print());

    }

    @DisplayName("18. order 생성 실패 - itemId null")
    @Test
    void save_fail_order_item_id_null() throws Exception {
        List<Item> items = itemFactory.listInit();
        OrderSaveRequestDto orderSaveRequestDto = orderFactory.getOrderSaveRequestDto(user, items);
        orderSaveRequestDto.getOrderItemSaveRequestDtos().get(0).setId(null);

        mvc.perform(post("/v1/orders")
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("id")));

    }

    @DisplayName("19. order 생성 실패 - item quantity null")
    @Test
    void save_fail_order_item_quantity_null() throws Exception {
        List<Item> items = itemFactory.listInit();
        OrderSaveRequestDto orderSaveRequestDto = orderFactory.getOrderSaveRequestDto(user, items);
        orderSaveRequestDto.getOrderItemSaveRequestDtos().get(0).setItemQuantity(null);

        mvc.perform(post("/v1/orders")
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("itemQuantity")));

    }

    @DisplayName("20. order 생성 실패 - item quantity 0")
    @Test
    void save_fail_order_item_quantity_zero() throws Exception {
        List<Item> items = itemFactory.listInit();
        OrderSaveRequestDto orderSaveRequestDto = orderFactory.getOrderSaveRequestDto(user, items);
        orderSaveRequestDto.getOrderItemSaveRequestDtos().get(0).setItemQuantity(0);

        mvc.perform(post("/v1/orders")
                .header("Authorization", tokenGenerator.getToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderSaveRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(containsString("errorList")))
                .andExpect(content().string(containsString("itemQuantity")));

    }

    @DisplayName("21. order confirm update 성공")
    @Test
    void order_confirm_update() throws Exception {

        List<Item> items = itemFactory.listInit();
        Order order = orderFactory.save(user, items);

        mvc.perform(patch("/v1/orders/" + order.getId())
                .header("Authorization", tokenGenerator.getToken(user)))
                .andExpect(status().isOk());

        assertEquals(OrderStatus.COMP, order.getOrderStatus());
        assertEquals(DeliveryStatus.COMP, order.getDelivery().getDeliveryStatus());

    }

    @DisplayName("22. order confirm update 실패 - 주문한 사용자와 요청 사용자가 일치하지 않음")
    @Test
    void order_confirm_update_fail_user_not_matched() throws Exception {

        List<Item> items = itemFactory.listInit();
        Order order = orderFactory.save(user, items);
        User otherUser = userFactory.createUser("test2@email.com", "test2name", "password12", "password12", "01011113333", "20200220");


        mvc.perform(patch("/v1/orders/" + order.getId())
                .header("Authorization", tokenGenerator.getToken(otherUser)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderException))
                .andExpect(content().string(containsString("ORDER_USER_REQUEST_USER_NOT_MATCHED")));

        assertEquals(OrderStatus.ORDER, order.getOrderStatus());

    }
    
    @DisplayName("23. order delete 성공")
    @Test
    void order_delete() throws Exception {

        List<Item> items = itemFactory.listInit();
        Order order = orderFactory.save(user, items);

        mvc.perform(delete("/v1/orders/" + order.getId())
                .header("Authorization", tokenGenerator.getToken(user)))
                .andExpect(status().isOk());

    }


    @DisplayName("24. order delete 실패 - 주문 사용자와 요청 사용자가 일치하지 않음")
    @Test
    void order_delete_fail_user_not_matched() throws Exception {

        List<Item> items = itemFactory.listInit();
        Order order = orderFactory.save(user, items);
        User otherUser = userFactory.createUser("test2@email.com", "test2name", "password12", "password12", "01011113333", "20200220");

        mvc.perform(delete("/v1/orders/" + order.getId())
                .header("Authorization", tokenGenerator.getToken(otherUser)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderException))
                .andExpect(content().string(containsString("ORDER_USER_REQUEST_USER_NOT_MATCHED")));

    }



}
