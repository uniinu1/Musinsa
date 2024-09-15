package Musinsa.MusinsaProject.biz.controller;

import Musinsa.MusinsaProject.biz.model.Product;
import Musinsa.MusinsaProject.biz.usecase.ProductUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Product")
@AllArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductUseCase productUseCase;
    //List로 받아 여러 개 등록 가능하도록 함
    @PostMapping
    @Operation(summary = "상품 추가")
    public void postBrand(@RequestBody List<Product> products){productUseCase.postProduct(products);}

    @PatchMapping
    @Operation(summary = "상품 수정")
    public ResponseEntity<Void> patchProduct(@RequestParam Long id, @RequestBody Product product){
        productUseCase.patchProduct(id, product);
        //요청이 성공적으로 처리되었지만, 추가로 반환할 데이터는 없을 때 리턴값
        return ResponseEntity.noContent().build();
    }

    //ids를 List로 받아 여러 개 삭제 가능하도록 함
    @DeleteMapping
    @Operation(summary = "상품 삭제")
    public ResponseEntity<Void> deleteBrand(@RequestBody List<Long> ids) {
        productUseCase.deleteProduct(ids);
        //요청이 성공적으로 처리되었지만, 추가로 반환할 데이터는 없을 때 리턴값
        return ResponseEntity.noContent().build();
    }
}
