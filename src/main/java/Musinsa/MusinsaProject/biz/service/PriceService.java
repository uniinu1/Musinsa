package Musinsa.MusinsaProject.biz.service;

import Musinsa.MusinsaProject.biz.model.packet.*;
import Musinsa.MusinsaProject.biz.repository.PriceRepository;
import Musinsa.MusinsaProject.biz.repository.PriceRepositoryJooq;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


@Service
public class PriceService {
    private final PriceRepository priceRepository;
    private final PriceRepositoryJooq priceRepositoryJooq;

    public PriceService(PriceRepository priceRepository, PriceRepositoryJooq priceRepositoryJooq) {
        this.priceRepository = priceRepository;
        this.priceRepositoryJooq = priceRepositoryJooq;
    }

    //구현 1) - 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
    public CategoriesLowPriceSummaryRes selectCategoriesLowPrice() {
        List<CategoriesLowPriceRes> products = priceRepository.findLowestPriceByCategory();

        /* 동일 최저가 처리 :
        한 브랜드 안에 같은 최저가가 여러 개라면,
        브랜드로 내림차순해서 하나 출력하도록 함 => 문제 예시에 스니커즈가 A : 9,000D원, G : 9,000원으로 최저가가 동일한데
        최저가 브랜드는 G라고 표기되어 있어서 브랜드로 내림차순하여 하나의 데이터만 출력되도록 함

        동일 최저가가 여러 개일 때 처리하는 정확한 기준이 있다면 다양한 비즈니스 로직을 적용할 수 있을 것으로 보임
        1. 최신 등록된 브랜드를 홍보하고 싶으면 '브랜드 등록일 내림차순'
        2. 인기 있는 브랜드를 홍보하고 싶으면 '브랜드의 인기순' => 인기순은 상품 판매량, 상품 평점, 상품 조회수 등을 기준으로 종합적으로 판단할 수 있을듯
         */
        //HashMap과 다르게 LinkedHashMap은 삽입된 순서를 유지하기에 사용
        Map<String, CategoriesLowPriceRes> filteredResults = new LinkedHashMap<>();
        for (CategoriesLowPriceRes result : products) {
            // 카테고리별로 하나만 저장되게 함
            filteredResults.putIfAbsent(result.getCategoryName(), result);
        }

        // 전체 가격 계산
        int totalPrice = filteredResults.values().stream()
                .mapToInt(CategoriesLowPriceRes::getPrice)
                .sum();

        return new CategoriesLowPriceSummaryRes(products, totalPrice);
    }

    // 구현 2) - 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
    // 최저가 하나만 가지고 오게 함
    public PriceBrandFinalRes selectLowestPriceBrand(){
        List<Object[]> results = priceRepository.findLowestPriceBrand();
        if(!results.isEmpty()){
            Object[] lowestBrand = results.get(0); // 첫 번째 결과가 가장 낮은 가격의 브랜드
            Long brandId = (Long) lowestBrand[0];
            String brandName = (String) lowestBrand[1];
            Long totalPrice = (Long) lowestBrand[2];

            List<LowestPriceBrandAllProductsRes> brand = priceRepository.findLowestPriceBrandAllProducts(brandId);

            // LowestPriceBrandRes 객체 생성
            LowestPriceBrandRes lowestPriceBrandRes = new LowestPriceBrandRes(brandName, brand, totalPrice);

            // PriceBrandFinalRes에 리스트로 감싸서 반환
            return new PriceBrandFinalRes(lowestPriceBrandRes);
        }

        return null;
    }

    // 구현 3) - 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
    // 동일 최저가가 여러 개면 모든 최저가 다 가지고 오게 함
    public CategoryPriceRes selectCategoryBestPrice(String categoryName) {
        List<BrandAndPriceRes> lowestPrice = priceRepositoryJooq.selectLowestPriceByCategory(categoryName);
        List<BrandAndPriceRes> highestPrice = priceRepositoryJooq.selectHighestPriceByCategory(categoryName);

        CategoryPriceRes response = new CategoryPriceRes();
        response.setCategoryName(categoryName);
        response.setLowestPrice(lowestPrice);
        response.setHighestPrice(highestPrice);

        return response;
    }
}