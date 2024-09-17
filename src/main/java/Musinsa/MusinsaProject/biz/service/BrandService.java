package Musinsa.MusinsaProject.biz.service;

import Musinsa.MusinsaProject.biz.model.Brand;
import Musinsa.MusinsaProject.biz.model.entity.BrandEntity;
import Musinsa.MusinsaProject.biz.model.mapper.AllMapper;
import Musinsa.MusinsaProject.biz.repository.BrandRepository;
import Musinsa.MusinsaProject.biz.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BrandService {
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final AllMapper allMapper;

    public BrandService(BrandRepository brandRepository, ProductRepository productRepository, AllMapper allMapper) {
        this.brandRepository = brandRepository;
        this.productRepository = productRepository;
        this.allMapper = allMapper;
    }

    public List<Brand> selectBrand() {
        // BrandEntity를 Brand로 변환
        List<BrandEntity> brandEntities = brandRepository.findAll();
        return brandEntities.stream()
                .map(allMapper::fromEntity) // BrandEntity를 Brand로 변환
                .collect(Collectors.toList());
    }

    public void insertBrand(List<Brand> brands) {
        //allMapper로 brands 값 BrandEntity로 매핑
        List<BrandEntity> brandEntities = brands.stream()
                .map(allMapper::toEntity)
                .collect(Collectors.toList());

        brandRepository.saveAll(brandEntities);
    }

    public void updateBrand(Long id, Brand brand){
        BrandEntity brandEntity = brandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("브랜드를 찾을 수 없습니다."));

        // 받은 Brand 데이터를 BrandEntity로 매핑
        BrandEntity updatedBrandEntity = allMapper.toEntity(brand);


        // updatedBrandEntity의 name 필드가 null인지 확인
        if (updatedBrandEntity.getName() == null) {
            throw new IllegalArgumentException("브랜드 이름은 필수입니다. 업데이트할 수 없습니다.");
        }

        // 기존의 엔티티를 업데이트
        brandEntity.setName(updatedBrandEntity.getName());
        brandEntity.setUpdateYmdt(LocalDateTime.now());  // 수정 시간 업데이트

        // 엔티티 저장
        brandRepository.save(brandEntity);
    }

    //삭제시 product 데이터 있으면 경고로 삭제 막음
    public void deleteAlertBrand(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("삭제할 브랜드 ID 목록이 비어 있습니다.");
        }

        // 각 ID에 대해 존재 여부 확인 후 삭제
        for (Long id : ids) {
            BrandEntity brandEntity = brandRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("브랜드를 찾을 수 없습니다. ID: " + id));

            if (productRepository.existsByBrandId(brandEntity)) {
                throw new IllegalArgumentException("이 브랜드에 속한 상품이 존재하므로 삭제할 수 없습니다. 상품 삭제 후 다시 시도해주세요.");
            }

            brandRepository.delete(brandEntity);
        }
    }

    //브랜드 삭제시 연계된 상품 모두 삭제
    public void deleteProductBrand(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("삭제할 브랜드 ID 목록이 비어 있습니다.");
        }

        // 각 ID에 대해 존재 여부 확인 후 삭제
        for (Long id : ids) {
            BrandEntity brandEntity = brandRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("브랜드를 찾을 수 없습니다. ID: " + id));

            // 해당 브랜드에 속한 모든 상품 삭제
            if (productRepository.existsByBrandId(brandEntity)) {
                productRepository.deleteByBrandId(brandEntity);
            }

            // 브랜드가 존재할 경우 삭제
            brandRepository.delete(brandEntity);
        }
    }

}
