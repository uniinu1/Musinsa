package Musinsa.MusinsaProject.biz.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//BrandController 통합테스트
@SpringBootTest
@AutoConfigureMockMvc
public class BrandControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    //브랜드 저장 테스트
    @Test
    public void testCreateBrand() throws Exception {
        // Given
        String brandJson = "[{\"name\":\"TEST\"}, {\"name\":\"TEST2\"}]";


        // When & Then
        // 성공 상태 코드 확인
        mockMvc.perform(post("/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //브랜드 수정 테스트
    @Test
    void testPatchBrand() throws Exception {
        // id 값과 수정할 brand 데이터를 JSON으로 만들어 요청
        Long brandId = 24L;
        String brandJson = "{\"name\":\"UPDATED_BRAND_NAME\"}";

        // PATCH 요청 테스트
        mockMvc.perform(patch("/brand")
                        .param("id", brandId.toString()) // requestParam으로 id 전달
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandJson)) // requestBody로 수정할 데이터를 전달
                .andExpect(status().isNoContent()); // 상태 코드 204 확인
    }

    // 브랜드 삭제 - 브랜드에 속한 상품이 있을 시 경고문구
    @Test
    void testDeleteAlertBrand() throws Exception {
        // 여러 개의 ID 삭제 요청
        String idsJson = "[47,48]";

        // alert endpoint에 DELETE 요청을 보내고, 결과를 검증
        mockMvc.perform(delete("/brand/alert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(idsJson))
                .andExpect(status().isNoContent())  // 204 상태 코드 확인
                .andExpect(MockMvcResultMatchers.content().string(""));  // 반환된 내용이 없는지 확인
    }


    // 브랜드 삭제 - 브랜드에 속한 상품이 있을 시 상품 삭제 후 브랜드 삭제
    @Test
    void testDeleteProductBrand() throws Exception {
        // 삭제할 브랜드 ID 리스트를 JSON으로 만듦
        String idsJson = "[38]";

        // DELETE 요청 테스트
        mockMvc.perform(delete("/brand/productDelete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(idsJson)) // requestBody로 삭제할 ID 리스트 전달
                .andExpect(status().isNoContent()); // 상태 코드 204 확인
    }
}
