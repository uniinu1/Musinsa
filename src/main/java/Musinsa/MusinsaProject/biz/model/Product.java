package Musinsa.MusinsaProject.biz.model;


import Musinsa.MusinsaProject.biz.model.entity.BrandEntity;
import Musinsa.MusinsaProject.biz.model.entity.CategoryEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Schema(title = "상품", description = "")
public class Product {
    @Schema(title = "고유번호", example = "1")
    private Long id;

    @Schema(title = "카테고리 고유번호")
    private CategoryEntity categoryId;

    @Schema(title = "브랜드 고유번호")
    private BrandEntity brandId;

    @Schema(title = "상품명")
    private String name;

    @Schema(title = "상품가격")
    private Integer price;
}
