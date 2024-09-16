package Musinsa.MusinsaProject.biz.controller;

import Musinsa.MusinsaProject.biz.model.packet.CategoriesLowPriceSummaryRes;
import Musinsa.MusinsaProject.biz.model.packet.CategoryPriceRes;
import Musinsa.MusinsaProject.biz.model.packet.PriceBrandFinalRes;
import Musinsa.MusinsaProject.biz.usecase.PriceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/price")
public class PriceController {
    private PriceUseCase priceUseCase;
    @GetMapping("/categories/lowest-price")
    @Operation(summary = "카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회")
    public CategoriesLowPriceSummaryRes getLowestPriceByCategory() {
        return priceUseCase.getCategoriesLowPrice();
    }

    @GetMapping("/brand/lowest-price")
    @Operation(summary = "단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회")
    public PriceBrandFinalRes getLowestPriceBrand(){
        return priceUseCase.getLowestPriceBrand();
    }

    @GetMapping("/categories/best-price")
    @Operation(summary = "카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회")
    public CategoryPriceRes getCategoryBestPrice(@RequestParam String categoryName){
        return priceUseCase.getCategoryBestPrice(categoryName);
    }

}
