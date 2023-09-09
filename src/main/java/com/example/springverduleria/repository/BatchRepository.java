package com.example.springverduleria.repository;

import com.example.springverduleria.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {
    // No se necesita implementar los métodos CRUD
}