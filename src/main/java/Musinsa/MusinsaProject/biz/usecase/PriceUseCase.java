package Musinsa.MusinsaProject.biz.usecase;

import Musinsa.MusinsaProject.biz.model.packet.CategoriesLowPriceSummaryRes;
import Musinsa.MusinsaProject.biz.model.packet.CategoryPriceRes;
import Musinsa.MusinsaProject.biz.model.packet.LowestPriceBrandRes;
import Musinsa.MusinsaProject.biz.model.packet.PriceBrandFinalRes;
import Musinsa.MusinsaProject.biz.service.PriceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@AllArgsConstructor
public class PriceUseCase {
    public PriceService priceService;

    //구현 1) - 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
    public CategoriesLowPriceSummaryRes getCategoriesLowPrice(){
        return priceService.selectCategoriesLowPrice();
    }

    //구현 2) - 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
    public PriceBrandFinalRes getLowestPriceBrand(){
        return priceService.selectLowestPriceBrand();
    }

    //구현 3) - 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
    public CategoryPriceRes getCategoryBestPrice(String categoryName){
        return priceService.selectCategoryBestPrice(categoryName);
    }
}
