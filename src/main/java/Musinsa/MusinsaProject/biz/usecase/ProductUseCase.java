package Musinsa.MusinsaProject.biz.usecase;


import Musinsa.MusinsaProject.biz.service.ProductService;
import Musinsa.MusinsaProject.biz.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
@AllArgsConstructor
public class ProductUseCase {
    private ProductService productService;

    public List<Product> getProduct() {
        return productService.selectProduct();
    }

    @Transactional
    public void postProduct(List<Product> products){
        productService.insertProduct(products);
    }

    @Transactional
    public void patchProduct(Long id, Product product){
        productService.updateProduct(id, product);
    }

    @Transactional
    public void deleteProduct(List<Long> ids) {
        productService.deleteProduct(ids);
    }
}
