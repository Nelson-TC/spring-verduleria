package com.example.springverduleria.service;

import com.example.springverduleria.model.Product;
import com.example.springverduleria.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByName(String name) {
        return productRepository.findByNameContaining(name);
    }


    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        product.setStock(0);
        product.setId(-1L);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        if (productRepository.existsById(id)) {
            product.setId(id); // Ensure the correct ID is set
            return productRepository.save(product);
        }
        return null; // Handle the case when the product doesn't exist
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
