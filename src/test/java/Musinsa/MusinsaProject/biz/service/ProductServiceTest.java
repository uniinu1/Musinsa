package Musinsa.MusinsaProject.biz.service;

import Musinsa.MusinsaProject.biz.model.Product;
import Musinsa.MusinsaProject.biz.model.entity.BrandEntity;
import Musinsa.MusinsaProject.biz.model.entity.CategoryEntity;
import Musinsa.MusinsaProject.biz.model.entity.ProductEntity;
import Musinsa.MusinsaProject.biz.model.mapper.AllMapper;
import Musinsa.MusinsaProject.biz.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.ArrayList;
import java.util.List;

//Product Service 단위테스트
@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private AllMapper allMapper;

    //insert 성공 테스트
    @Test
    public void testInsertProduct_ValidProducts_Success() throws JsonProcessingException {
        // Given
        String jsonProducts = "[{"
                + "\"name\": \"아우터TEST\","
                + "\"price\": 1000,"
                + "\"categoryId\": { \"id\": 2 },"
                + "\"brandId\": { \"id\": 3 }"
                + "}]";

        // JSON 문자열을 Product 객체 리스트로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        List<Product> products = objectMapper.readValue(jsonProducts,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));

        // Mock 설정
        ProductEntity productEntity = new ProductEntity();
        Mockito.when(allMapper.toEntity(Mockito.any(Product.class))).thenReturn(productEntity);

        // When
        productService.insertProduct(products);

        // Then => productRepository의 saveAll 메서드가 테스트 과정에서 한 번 호출되었으며, 리스트 형식의 인자를 사용했는지 확인
        Mockito.verify(productRepository, Mockito.times(1)).saveAll(Mockito.anyList());
    }

    //insert 실패 테스트 => brandId가 null일때
    @Test
    public void testInsertProduct_InvalidBrandId_ThrowsException() {
        // Given
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setName("Test Product");
        product.setBrandId(null);  // Invalid BrandId 설정

        // CategoryEntity 사용
        CategoryEntity category = new CategoryEntity();
        category.setId(1L);  // Category ID 설정
        product.setCategoryId(category);  // CategoryEntity 객체 설정

        products.add(product);

        // When & Then
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            productService.insertProduct(products);
        });

        Assertions.assertEquals("Brand ID는 필수입니다. Product: Test Product", exception.getMessage());
    }

    //insert 실패 테스트 => categoryId null일때
    @Test
    public void testInsertProduct_InvalidCategoryId_ThrowsException() {
        // Given
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setName("Test Product");
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(1L);
        product.setBrandId(brandEntity);
        product.setCategoryId(null);  // Invalid CategoryId
        products.add(product);

        // When & Then
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            productService.insertProduct(products);
        });

        Assertions.assertEquals("Category ID는 필수입니다. Product: Test Product", exception.getMessage());
    }

}
