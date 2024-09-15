package Musinsa.MusinsaProject.biz.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    @Comment("카테고리 고유번호")
    private CategoryEntity categoryId;

    @ManyToOne
    @JoinColumn(name = "brandId", nullable = false)
    @Comment("브랜드 고유번호")
    private BrandEntity brandId;

    @Comment("상품명")
    private String name;

    @Comment("상품가격")
    private Integer price;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime registerYmdt;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateYmdt;
}
