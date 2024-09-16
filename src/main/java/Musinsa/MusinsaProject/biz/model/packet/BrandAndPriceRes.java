package Musinsa.MusinsaProject.biz.model.packet;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// 구현 3) - 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API RESPONSE
@Getter
@Setter
@AllArgsConstructor
public class BrandAndPriceRes {
    @JsonProperty("브랜드")
    private String brandName;
    @JsonProperty("가격")
    private Integer price;
}
