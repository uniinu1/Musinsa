package Musinsa.MusinsaProject.biz.model.packet;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

//구현 2) - 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API RESPONSE
@Getter
@Setter
public class LowestPriceBrandRes {
    @JsonProperty("브랜드")
    private String brandName;
    @JsonProperty("카테고리")
    private List<LowestPriceBrandAllProductsRes> category;
    @JsonProperty("총액")
    private Long totalPrice;

    public LowestPriceBrandRes(String brandName, List<LowestPriceBrandAllProductsRes> category, Long totalPrice) {
        this.brandName = brandName;
        this.category = category;
        this.totalPrice = totalPrice;
    }
}
