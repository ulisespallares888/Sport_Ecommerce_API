package com.sportecommerce.proyecto.v1.modules.products.repository;

import com.sportecommerce.proyecto.v1.modules.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long> {}
