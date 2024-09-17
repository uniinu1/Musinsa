package Musinsa.MusinsaProject.biz.model;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Schema(title = "카테고리", description = "")
public class Category {
    @Schema(title = "고유번호")
    private Long id;

    @Schema(title = "카테고리명")
    private String name;

    @Schema(title = "등록일")
    private LocalDateTime registerYmdt;

    @Schema(title = "수정일")
    private LocalDateTime updateYmdt;

}
