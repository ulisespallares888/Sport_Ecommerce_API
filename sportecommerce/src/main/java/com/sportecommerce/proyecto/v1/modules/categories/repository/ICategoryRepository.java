package com.sportecommerce.proyecto.v1.modules.categories.repository;

import com.sportecommerce.proyecto.v1.modules.categories.model.Category;
import com.sportecommerce.proyecto.v1.modules.products.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameAndActiveTrue(String name);
    Page<Category> findAllByActiveTrue(Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.name IN :names")
    Page<Product> findProductsByCategories(List<String> names, Pageable pageable);

}

