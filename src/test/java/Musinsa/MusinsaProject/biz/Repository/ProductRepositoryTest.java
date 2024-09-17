package Musinsa.MusinsaProject.biz.Repository;

import Musinsa.MusinsaProject.biz.model.Product;
import Musinsa.MusinsaProject.biz.model.entity.BrandEntity;
import Musinsa.MusinsaProject.biz.model.entity.CategoryEntity;
import Musinsa.MusinsaProject.biz.model.entity.ProductEntity;
import Musinsa.MusinsaProject.biz.model.mapper.AllMapper;
import Musinsa.MusinsaProject.biz.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @MockBean
    private AllMapper allMapper;

    //유효하지 않은 카테고리 ID insert 테스트
    @Test
    public void testInsertProduct_InvalidCategoryId_ThrowsException() {
        // Given
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(1000);

        // 유효한 Brand 설정
        BrandEntity brand = new BrandEntity();
        brand.setId(1L);
        product.setBrandId(brand);

        // 유효하지 않은 Category 설정
        CategoryEntity invalidCategory = new CategoryEntity();
        invalidCategory.setId(999L);  // 유효하지 않은 카테고리 ID
        product.setCategoryId(invalidCategory);

        // Product -> ProductEntity로 변환(Mock)
        ProductEntity productEntity = new ProductEntity();
        Mockito.when(allMapper.toEntity(Mockito.any(Product.class))).thenReturn(productEntity);

        // When & Then: 데이터베이스 무결성 예외 발생 확인
        org.junit.jupiter.api.Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            productRepository.saveAll(List.of(productEntity));  // saveAll에 리스트로 저장
        });
    }
}
