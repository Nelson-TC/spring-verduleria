package com.example.springverduleria.repository;

import com.example.springverduleria.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // No se necesita implementar los métodos CRUD
}