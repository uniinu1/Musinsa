package Musinsa.MusinsaProject.biz.model.mapper;

import Musinsa.MusinsaProject.biz.model.Brand;
import Musinsa.MusinsaProject.biz.model.Category;
import Musinsa.MusinsaProject.biz.model.Product;
import Musinsa.MusinsaProject.biz.model.entity.BrandEntity;
import Musinsa.MusinsaProject.biz.model.entity.CategoryEntity;
import Musinsa.MusinsaProject.biz.model.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.ReportingPolicy.IGNORE;
@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface AllMapper {
    Brand fromEntity(BrandEntity brandEntity);
    @Mapping(target = "registerYmdt", source = "registerYmdt")
    @Mapping(target = "updateYmdt", source = "updateYmdt")
    BrandEntity toEntity(Brand brand);

    Category fromEntity(CategoryEntity categoryEntity);
    CategoryEntity toEntity(Category category);

    Product fromEntity(ProductEntity productEntity);
    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "brandId.id", target = "brandId.id")
    @Mapping(source = "categoryId.id", target = "categoryId.id")
    ProductEntity toEntity(Product product);
}
