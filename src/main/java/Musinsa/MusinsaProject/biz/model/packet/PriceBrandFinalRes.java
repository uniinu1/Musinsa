package Musinsa.MusinsaProject.biz.model.packet;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

//구현 2) - 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API 최종 RESPONSE
@Getter
@Setter
public class PriceBrandFinalRes {
    @JsonProperty("최저가")
    private LowestPriceBrandRes lowestBrand;

    public PriceBrandFinalRes(LowestPriceBrandRes lowestBrand) {
        this.lowestBrand = lowestBrand;
    }
}
