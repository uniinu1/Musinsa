package Musinsa.MusinsaProject.biz.repository;

import Musinsa.MusinsaProject.biz.model.entity.BrandEntity;
import Musinsa.MusinsaProject.biz.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    //brandId로 상품 존재여부 확인
    boolean existsByBrandId(BrandEntity brandId);

    // 해당 브랜드에 속한 모든 상품 삭제
    void deleteByBrandId(BrandEntity brandId);
}
