package Musinsa.MusinsaProject.biz.service;

import Musinsa.MusinsaProject.biz.model.Product;

import Musinsa.MusinsaProject.biz.model.mapper.AllMapper;
import Musinsa.MusinsaProject.biz.repository.BrandRepository;
import Musinsa.MusinsaProject.biz.repository.CategoryRepository;
import Musinsa.MusinsaProject.biz.repository.ProductRepository;
import org.springframework.stereotype.Service;
import Musinsa.MusinsaProject.biz.model.entity.ProductEntity;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final AllMapper allMapper;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, BrandRepository brandRepository, AllMapper allMapper) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.allMapper = allMapper;
    }

    public void insertProduct(List<Product> products) {
        try {
            List<ProductEntity> productEntities = products.stream()
                    .map(product -> {
                        //category, brand가 null값을 때 에러 처리
                        if (product.getBrandId() == null || product.getBrandId().getId() == null) {
                            throw new IllegalArgumentException("Brand ID는 필수입니다. Product: " + product.getName());
                        }
                        if(product.getCategoryId() == null || product.getCategoryId().getId() == null){
                            throw new IllegalArgumentException("Category ID는 필수입니다. Product: " + product.getName());
                        }
                        // Product를 ProductEntity로 매핑
                        return allMapper.toEntity(product);
                    })
                    .collect(Collectors.toList());


            productRepository.saveAll(productEntities);

        } catch (DataIntegrityViolationException ex) {
            // 카테고리 ID 관련 외래 키 위반 오류 처리
            if (ex.getCause() != null && ex.getCause().getMessage().contains("CATEGORY_ENTITY")) {
                throw new RuntimeException("Invalid categoryId: 카테고리가 존재하지 않습니다. id를 확인해주세요.");
            }

            // 브랜드 ID 관련 외래 키 위반 오류 처리
            if (ex.getCause() != null && ex.getCause().getMessage().contains("BRAND_ENTITY")) {
                throw new RuntimeException("Invalid categoryId: 브랜드가 존재하지 않습니다. id를 확인해주세요.");
            }

            // 그 외의 데이터 무결성 예외
            throw new RuntimeException("Data integrity violation occurred");
        }

    }

    public void updateProduct(Long id, Product product){
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품 찾을 수 없습니다."));

        if (product.getCategoryId() != null) {
            boolean categoryExists = categoryRepository.existsById(product.getCategoryId().getId());
            if (!categoryExists) {
                throw new RuntimeException("Invalid categoryId: 카테고리가 존재하지 않습니다. id를 확인해주세요.");
            }
        }

        // 브랜드 ID 확인
        if (product.getBrandId() != null) {
            boolean brandExists = brandRepository.existsById(product.getBrandId().getId());
            if (!brandExists) {
                throw new RuntimeException("Invalid brandId: 브랜드가 존재하지 않습니다. id를 확인해주세요.");
            }
        }

        try {
            // 받은 Product 데이터를 ProductEntity로 매핑
            ProductEntity updateProductEntity = allMapper.toEntity(product);

            // 부분 수정 허용 = 받은 Product 데이터를 확인하여 null이 아닌 값만 업데이트
            if (product.getName() != null) {
                productEntity.setName(updateProductEntity.getName());
            }

            if (product.getPrice() != null) {
                productEntity.setPrice(updateProductEntity.getPrice());
            }

            if (product.getCategoryId() != null) {
                productEntity.setCategoryId(updateProductEntity.getCategoryId());
            }

            if (product.getBrandId() != null) {
                productEntity.setBrandId(updateProductEntity.getBrandId());
            }

            productEntity.setUpdateYmdt(LocalDateTime.now());  // 수정 시간 업데이트

            // 엔티티 저장
            productRepository.save(productEntity);

        }catch(DataIntegrityViolationException ex){
            // 그 외의 데이터 무결성 예외
            throw new RuntimeException("데이터 무결성 위반: 잘못된 데이터가 존재합니다.");
        }
    }

    public void deleteProduct(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("삭제할 상품 ID 목록이 비어 있습니다.");
        }

        // 각 ID에 대해 존재 여부 확인 후 삭제
        for (Long id : ids) {
            ProductEntity productEntity = productRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + id));


            productRepository.delete(productEntity);
        }
    }
}
