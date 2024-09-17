package Musinsa.MusinsaProject.biz.usecase;

import Musinsa.MusinsaProject.biz.model.Brand;
import Musinsa.MusinsaProject.biz.service.BrandService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
@AllArgsConstructor
public class BrandUseCase {
    private BrandService brandService;

    public List<Brand> getBrand() {
        return brandService.selectBrand();
    }
    @Transactional
    public void postBrand(List<Brand> brands){
        brandService.insertBrand(brands);
    }

    @Transactional
    public void patchBrand(Long id, Brand brand){
        brandService.updateBrand(id, brand);
    }

    @Transactional
    public void deleteAlertBrand(List<Long> ids) {
        brandService.deleteAlertBrand(ids);
    }

    @Transactional
    public void deleteProductBrand(List<Long> ids) {
        brandService.deleteProductBrand(ids);
    }
}
