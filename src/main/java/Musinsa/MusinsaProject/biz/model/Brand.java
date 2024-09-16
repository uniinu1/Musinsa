package Musinsa.MusinsaProject.biz.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Schema(title = "브랜드", description = "")
public class Brand {
    @Schema(title = "고유번호", example = "1")
    private Long id;

    @Schema(title = "브랜드명")
    private String name;

    @Schema(title = "등록일")
    private LocalDateTime registerYmdt;

    @Schema(title = "수정일")
    private LocalDateTime updateYmdt;

    // 모든 필드를 받는 생성자
    public Brand(Long id, String name, LocalDateTime registerYmdt, LocalDateTime updateYmdt) {
        this.id = id;
        this.name = name;
        this.registerYmdt = registerYmdt;
        this.updateYmdt = updateYmdt;
    }
}
