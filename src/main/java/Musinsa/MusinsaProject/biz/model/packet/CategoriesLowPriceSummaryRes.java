package Musinsa.MusinsaProject.biz.model.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// 구현 1) - 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API 최종 Response
@Getter
@Setter
public class CategoriesLowPriceSummaryRes {
    private List<CategoriesLowPriceRes> products;
    private Integer totalPrice;

    public CategoriesLowPriceSummaryRes(List<CategoriesLowPriceRes> products, Integer totalPrice) {
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
