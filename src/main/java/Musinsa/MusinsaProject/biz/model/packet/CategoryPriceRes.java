package Musinsa.MusinsaProject.biz.model.packet;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// 구현 3) - 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API 최종 RESPONSE
@Getter
@Setter
public class CategoryPriceRes {
    @JsonProperty("카테고리")
    private String categoryName;
    @JsonProperty("최저가")
    private List<BrandAndPriceRes> lowestPrice;
    @JsonProperty("최고가")
    private List<BrandAndPriceRes> highestPrice;
}
