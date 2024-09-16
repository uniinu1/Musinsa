package Musinsa.MusinsaProject.biz.controller;

import Musinsa.MusinsaProject.biz.model.Brand;
import Musinsa.MusinsaProject.biz.usecase.BrandUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@Tag(name="Brand")
@AllArgsConstructor
@RestController
@RequestMapping("/brand")
public class BrandController {
    private BrandUseCase brandUseCase;
    //List로 받아 여러 개 등록 가능하도록 함
    @PostMapping
    @Operation(summary = "브랜드 추가")
    public void postBrand(@RequestBody List<Brand> brands){brandUseCase.postBrand(brands);}

    @PatchMapping
    @Operation(summary = "브랜드 수정")
    public ResponseEntity<Void> patchBrand(@RequestParam Long id, @RequestBody Brand brand){
        brandUseCase.patchBrand(id, brand);
        //요청이 성공적으로 처리되었지만, 추가로 반환할 데이터는 없을 때 리턴값
        return ResponseEntity.noContent().build();
    }

    // ids를 List로 받아 여러 개 삭제 가능하도록 함
    // Brand에 속한 Product가 있을 때 해결방안 1 : 삭제 방지 경고 메시지 반환
    @DeleteMapping("/alert")
    @Operation(summary = "브랜드 삭제")
    public ResponseEntity<Void> deleteAlertBrand(@RequestBody List<Long> ids) {
        brandUseCase.deleteAlertBrand(ids);
        //요청이 성공적으로 처리되었지만, 추가로 반환할 데이터는 없을 때 리턴값
        return ResponseEntity.noContent().build();
    }

    // ids를 List로 받아 여러 개 삭제 가능하도록 함
    // Brand에 속한 Product가 있을 때 해결방안 2 : 상품 삭제 후 브랜드 삭제
    @DeleteMapping("/productDelete")
    @Operation(summary = "브랜드 삭제")
    public ResponseEntity<Void> deleteProductBrand(@RequestBody List<Long> ids) {
        brandUseCase.deleteProductBrand(ids);
        //요청이 성공적으로 처리되었지만, 추가로 반환할 데이터는 없을 때 리턴값
        return ResponseEntity.noContent().build();
    }
}
