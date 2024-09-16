package Musinsa.MusinsaProject.biz.repository;

import Musinsa.MusinsaProject.biz.model.Product;
import Musinsa.MusinsaProject.biz.model.entity.ProductEntity;
import Musinsa.MusinsaProject.biz.model.packet.CategoriesLowPriceRes;
import Musinsa.MusinsaProject.biz.model.packet.LowestPriceBrandAllProductsRes;
import Musinsa.MusinsaProject.biz.model.packet.LowestPriceBrandRes;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PriceRepository extends JpaRepository<ProductEntity, Long> {

    //구현 1) - 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회
    @Query("SELECT new Musinsa.MusinsaProject.biz.model.packet.CategoriesLowPriceRes(c.name, b.name, p.price) " +
            "FROM ProductEntity p " +
            "JOIN CategoryEntity c ON p.categoryId.id = c.id " +
            "JOIN BrandEntity b ON p.brandId.id = b.id " +
            "WHERE p.price = (SELECT MIN(p2.price) FROM ProductEntity p2 WHERE p2.categoryId = p.categoryId) " +
            "GROUP BY c.name, b.name, p.price " +
            "ORDER BY b.name DESC")
    List<CategoriesLowPriceRes> findLowestPriceByCategory();

    //구현 2) - 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 '브랜드'와 '총액'
    @Query("SELECT p.brandId.id, b.name, SUM(p.price) AS totalPrice " +
            "FROM ProductEntity p " +
            "JOIN BrandEntity b on p.brandId.id = b.id " +
            "GROUP BY b.id " +
            "ORDER BY totalPrice ASC")
    List<Object[]> findLowestPriceBrand();

    //구현 2) - 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드의 카테고리별 상품 가격
    @Query("SELECT new Musinsa.MusinsaProject.biz.model.packet.LowestPriceBrandAllProductsRes(c.name, p.price) " +
            "FROM ProductEntity p " +
            "JOIN CategoryEntity c on p.categoryId.id = c.id " +
            "WHERE p.brandId.id = :brandId")
    List<LowestPriceBrandAllProductsRes> findLowestPriceBrandAllProducts(@Param("brandId") Long brandId);

}
