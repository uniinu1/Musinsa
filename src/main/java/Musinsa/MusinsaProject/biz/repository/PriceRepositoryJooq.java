package Musinsa.MusinsaProject.biz.repository;

import Musinsa.MusinsaProject.biz.model.packet.BrandAndPriceRes;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;


@Repository
public class PriceRepositoryJooq {
    @Autowired
    private DSLContext dsl;

    //구현 3) - 카테고리 이름으로 최저 상품 가격 조회
    public List<BrandAndPriceRes> selectLowestPriceByCategory(String category) {
        // 1. 먼저 해당 카테고리의 최저가를 조회합니다.
        BigDecimal lowestPrice = dsl.select(field("\"P\".\"PRICE\""))
                .from(table("\"PRODUCT_ENTITY\"").as("P"))
                .join(table("\"CATEGORY_ENTITY\"").as("C")).on(field("\"P\".\"CATEGORY_ID\"").eq(field("\"C\".\"ID\"")))
                .where(field("\"C\".\"NAME\"").eq(category))
                .orderBy(field("\"P\".\"PRICE\"").asc())
                .limit(1)
                .fetchOneInto(BigDecimal.class);

        // 2. 최저가와 동일한 모든 상품을 조회합니다.
        return dsl.select(field("\"B\".\"NAME\"").as("brandName"),
                        field("\"P\".\"PRICE\"").as("price"))
                .from(table("\"PRODUCT_ENTITY\"").as("P"))
                .join(table("\"BRAND_ENTITY\"").as("B")).on(field("\"P\".\"BRAND_ID\"").eq(field("\"B\".\"ID\"")))
                .join(table("\"CATEGORY_ENTITY\"").as("C")).on(field("\"P\".\"CATEGORY_ID\"").eq(field("\"C\".\"ID\"")))
                .where(field("\"C\".\"NAME\"").eq(category)
                        .and(field("\"P\".\"PRICE\"").eq(lowestPrice)))  // 최저가와 동일한 가격을 가진 상품을 조회
                .fetchInto(BrandAndPriceRes.class);
    }

    //구현 3) - 카테고리 이름으로 최고 상품 가격 조회
    public List<BrandAndPriceRes> selectHighestPriceByCategory(String category) {
        // 1. 먼저 해당 카테고리의 최고가를 조회합니다.
        BigDecimal highestPrice = dsl.select(field("\"P\".\"PRICE\""))
                .from(table("\"PRODUCT_ENTITY\"").as("P"))
                .join(table("\"CATEGORY_ENTITY\"").as("C")).on(field("\"P\".\"CATEGORY_ID\"").eq(field("\"C\".\"ID\"")))
                .where(field("\"C\".\"NAME\"").eq(category))
                .orderBy(field("\"P\".\"PRICE\"").desc())
                .limit(1)
                .fetchOneInto(BigDecimal.class);

        // 2. 최고가와 동일한 모든 상품을 조회합니다.
        return dsl.select(field("\"B\".\"NAME\"").as("brandName"),
                        field("\"P\".\"PRICE\"").as("price"))
                .from(table("\"PRODUCT_ENTITY\"").as("P"))
                .join(table("\"BRAND_ENTITY\"").as("B")).on(field("\"P\".\"BRAND_ID\"").eq(field("\"B\".\"ID\"")))
                .join(table("\"CATEGORY_ENTITY\"").as("C")).on(field("\"P\".\"CATEGORY_ID\"").eq(field("\"C\".\"ID\"")))
                .where(field("\"C\".\"NAME\"").eq(category)
                        .and(field("\"P\".\"PRICE\"").eq(highestPrice)))  // 최고가와 동일한 가격을 가진 상품을 조회
                .fetchInto(BrandAndPriceRes.class);
    }

}
