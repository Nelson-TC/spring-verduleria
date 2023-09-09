package com.example.springverduleria.service;

import com.example.springverduleria.dto.AddProductToBatchRequest;
import com.example.springverduleria.model.Batch;
import com.example.springverduleria.model.BatchProduct;
import com.example.springverduleria.model.Product;
import com.example.springverduleria.repository.BatchProductRepository;
import com.example.springverduleria.repository.BatchRepository;
import com.example.springverduleria.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BatchService {

    private final BatchRepository batchRepository;
    private ProductRepository productRepository;
    private BatchProductRepository batchProductRepository;

    @Autowired
    public BatchService(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    public List<Batch> getAllBatches() {
        return batchRepository.findAll();
    }

    public Optional<Batch> getBatchById(Long id) {
        return batchRepository.findById(id);
    }

    public Batch createBatch(Batch batch) {
        return batchRepository.save(batch);
    }

    public Optional<Batch> updateBatch(Long id, Batch updatedBatch) {
        Optional<Batch> existingBatch = batchRepository.findById(id);
        if (existingBatch.isPresent()) {
            Batch batch = existingBatch.get();
            batch.setEntryDate(updatedBatch.getEntryDate());
            batch.setStatus(updatedBatch.getStatus());
            // Update other properties as needed
            return Optional.of(batchRepository.save(batch));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteBatch(Long id) {
        Optional<Batch> batch = batchRepository.findById(id);
        if (batch.isPresent()) {
            batchRepository.delete(batch.get());
            return true;
        } else {
            return false;
        }
    }

    public void addProductToBatch(Long batchId, AddProductToBatchRequest request) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Crear el nuevo registro en la tabla intermedia
        BatchProduct batchProduct = new BatchProduct();
        batchProduct.setBatch(batch);
        batchProduct.setProduct(product);
        batchProduct.setExpireDate(request.getExpireDate());
        batchProduct.setOutDate(request.getOutDate());
        batchProduct.setQuantity(request.getQuantity());
        batchProduct.setOutQuantity(request.getOutQuantity());

        // Guardar el registro en la tabla intermedia
        // Aquí debes utilizar tu repositorio de la tabla intermedia o hacer la operación necesaria
        // para guardar el registro en la base de datos

        // Ejemplo si tuvieras un BatchProductRepository
        batchProductRepository.save(batchProduct);
    }
}
