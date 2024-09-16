package Musinsa.MusinsaProject.biz.model.packet;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//구현 2) - 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API RESPONSE
@Getter
@Setter
@AllArgsConstructor
public class LowestPriceBrandAllProductsRes {
    @JsonProperty("카테고리")
    private String categoryName;
    @JsonProperty("가격")
    private Integer price;
}
