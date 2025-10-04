package com.sportecommerce.proyecto.v1.modules.categories.repository;

import com.sportecommerce.proyecto.v1.modules.categories.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}

