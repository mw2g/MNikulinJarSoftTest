package com.nikulin.MNikulinJarSoftTest.repository;

import com.nikulin.MNikulinJarSoftTest.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);

    Optional<Category> findByReqName(String reqName);

    List<Category> findAllByDeleted(Boolean deleted);

    Optional<Category> findByCategoryIdAndDeleted(Integer categoryId, Boolean deleted);
}
