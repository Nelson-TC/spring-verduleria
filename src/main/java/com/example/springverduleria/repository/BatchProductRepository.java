package com.example.springverduleria.repository;

import com.example.springverduleria.model.BatchProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchProductRepository extends JpaRepository<BatchProduct, Long> {
    // No se necesita implementar los métodos CRUD
}