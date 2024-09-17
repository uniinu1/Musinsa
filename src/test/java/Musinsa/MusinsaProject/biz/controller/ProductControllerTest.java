package Musinsa.MusinsaProject.biz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//ProductController 통합테스트
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    //상품 저장 테스트
    @Test
    public void testCreateProduct() throws Exception {
        // Given
        String productJson = "[{\"name\":\"아우터TEST\", \"price\":1000, \"categoryId\":{\"id\":2}, \"brandId\":{\"id\":2}}]"
                ;

        // When & Then
        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //상품 수정 테스트
    @Test
    void testPatchProduct() throws Exception {
        Long productId = 110L;
        String productJson = "{\"name\":\"바지TEST\", \"price\":1000, \"categoryId\":{\"id\":3}, \"brandId\":{\"id\":2}}";

        // PATCH 요청 테스트
        mockMvc.perform(patch("/product")
                        .param("id", productId.toString()) // requestParam으로 id 전달
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson)) // requestBody로 수정할 데이터를 전달
                .andExpect(status().isNoContent()); // 상태 코드 204 확인
    }

    // 상품 삭제
    @Test
    void testDeleteAlertProduct() throws Exception {
        // 여러 개의 ID 삭제 요청
        String idsJson = "[112]";

        // alert endpoint에 DELETE 요청을 보내고, 결과를 검증
        mockMvc.perform(delete("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(idsJson))
                .andExpect(status().isNoContent())  // 204 상태 코드 확인
                .andExpect(MockMvcResultMatchers.content().string(""));  // 반환된 내용이 없는지 확인
    }
}
