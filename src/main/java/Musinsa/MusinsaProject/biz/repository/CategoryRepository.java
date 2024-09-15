package Musinsa.MusinsaProject.biz.repository;

import Musinsa.MusinsaProject.biz.model.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<BrandEntity,Long> {
}
