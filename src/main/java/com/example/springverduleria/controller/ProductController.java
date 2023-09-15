package com.example.springverduleria.controller;

import com.example.springverduleria.exception.ErrorFormatter;
import com.example.springverduleria.model.Product;
import com.example.springverduleria.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/api/products")
    @CrossOrigin(origins = "*")
@RestController
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> errorBody = ErrorFormatter.formatValidationErrors(bindingResult.getFieldErrors());
            return ResponseEntity.badRequest().body(errorBody);
        }
        Product newProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product, BindingResult bindingResult) {

        Optional<Product> existingProduct = productService.getProductById(id);
        if (existingProduct.isPresent()) {
            if (bindingResult.hasErrors()) {
                Map<String, Object> errorBody = ErrorFormatter.formatValidationErrors(bindingResult.getFieldErrors());
                return ResponseEntity.badRequest().body(errorBody);
            }
            Product updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam(required = false) String search_query) {
        if (search_query != null && !search_query.isEmpty()) {
            List<Product> searchResults = new ArrayList<>();
            try {
                Long id = Long.parseLong(search_query);
                Optional<Product> productById = productService.getProductById(id);
                productById.ifPresent(searchResults::add);
            } catch (NumberFormatException e) {
            }

            List<Product> productsByName = productService.getProductsByName(search_query);
            searchResults.addAll(productsByName);

            if (!searchResults.isEmpty()) {
                return ResponseEntity.ok(searchResults);
            } else {
                /* Return empty list in case of non coincidences */
                return ResponseEntity.ok(Collections.emptyList());
            }
        }

        /* Return all the products in case of no search_query */
        List<Product> allProducts = productService.getAllProducts();
        return ResponseEntity.ok(allProducts);
    }


}
