package com.example.springverduleria.controller;

import com.example.springverduleria.dto.AddProductToBatchRequest;
import com.example.springverduleria.model.Batch;
import com.example.springverduleria.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/batches")
public class BatchController {

    private final BatchService batchService;

    @Autowired
    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @GetMapping
    public List<Batch> getAllBatches() {
        return batchService.getAllBatches();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Batch> getBatchById(@PathVariable Long id) {
        Optional<Batch> batch = batchService.getBatchById(id);
        if (batch.isPresent()) {
            return ResponseEntity.ok(batch.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Batch> createBatch(@RequestBody Batch batch) {
        Batch newBatch = batchService.createBatch(batch);
        return ResponseEntity.ok(newBatch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Batch> updateBatch(@PathVariable Long id, @RequestBody Batch batch) {
        Optional<Batch> updatedBatch = batchService.updateBatch(id, batch);
        if (updatedBatch.isPresent()) {
            return ResponseEntity.ok(updatedBatch.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBatch(@PathVariable Long id) {
        boolean deleted = batchService.deleteBatch(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/add-product")
    public ResponseEntity<String> addProductToBatch(@PathVariable Long batchId, @RequestBody AddProductToBatchRequest request) {
        batchService.addProductToBatch(batchId, request);
        return ResponseEntity.ok("Producto agregado al lote exitosamente.");
    }
}