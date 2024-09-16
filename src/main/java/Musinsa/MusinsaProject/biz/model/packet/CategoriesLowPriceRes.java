package Musinsa.MusinsaProject.biz.model.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// 구현 1) - 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API Response
@Getter
@Setter
@AllArgsConstructor
public class CategoriesLowPriceRes {
    private String categoryName;
    private String brandName;
    private Integer price;
}
