package com.example.springverduleria.controller;

import com.example.springverduleria.controller.models.InventoryOperation;
import com.example.springverduleria.controller.models.InventoryRequest;
import com.example.springverduleria.model.Product;
import com.example.springverduleria.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final ProductService productService;

    @Autowired
    public InventoryController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/increase")
    public ResponseEntity<?> increaseInventory(@RequestBody InventoryRequest request) {
        List<String> errors = new ArrayList<>();

        for (InventoryOperation operation : request.getContent()) {
            Optional<Product> productOptional = productService.getProductById(operation.getId());
            if (!(productOptional.isPresent())) {
                errors.add("Producto con ID " + operation.getId() + " no encontrado.");
            }
        }

        if (!errors.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Algunos productos no se encontraron en la base de datos.");
            errorResponse.put("errors", errors);
            return ResponseEntity.badRequest().body(errorResponse);
        } else {
            for (InventoryOperation operation : request.getContent()) {
                Product product = productService.getProductById(operation.getId()).get();
                product.setStock(product.getStock() + operation.getQuantity());
                productService.updateProduct(operation.getId(), product);
            }
            return ResponseEntity.ok("Inventario aumentado exitosamente");
        }
    }



    @PostMapping("/decrease")
    public ResponseEntity<?> decreaseInventory(@RequestBody InventoryRequest request) {
        List<String> errors = new ArrayList<>();

        for (InventoryOperation operation : request.getContent()) {
            Optional<Product> productOptional = productService.getProductById(operation.getId());

            if (productOptional.isPresent()) {
                Product product = productOptional.get();

                if (operation.getQuantity() > product.getStock()) {
                    errors.add("Stock insuficiente para el producto con ID: " + product.getId());
                }
            } else {
                errors.add("Producto con ID " + operation.getId() + " no encontrado.");
            }
        }

        if (!errors.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errors", errors);
            return ResponseEntity.badRequest().body(errorResponse);
        } else {
            for (InventoryOperation operation : request.getContent()) {
                Product product = productService.getProductById(operation.getId()).get();
                product.setStock(product.getStock() - operation.getQuantity());
                productService.updateProduct(operation.getId(), product);
            }
            return ResponseEntity.ok("Inventario disminuido exitosamente");
        }
    }



}
