package com.example.springverduleria.controller;

import com.example.springverduleria.exception.ErrorFormatter;
import com.example.springverduleria.model.Category;
import com.example.springverduleria.model.Product;
import com.example.springverduleria.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            return ResponseEntity.ok(category.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> errorBody = ErrorFormatter.formatValidationErrors(bindingResult.getFieldErrors());
            return ResponseEntity.badRequest().body(errorBody);
        }
        Category newCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @Valid @RequestBody Category category, BindingResult bindingResult) {
        Optional<Category> existingCategory = categoryService.getCategoryById(id);
        if (existingCategory.isPresent()) {
            if (bindingResult.hasErrors()) {
                Map<String, Object> errorBody = ErrorFormatter.formatValidationErrors(bindingResult.getFieldErrors());
                return ResponseEntity.badRequest().body(errorBody);
            }
            Category updatedCategory = categoryService.updateCategory(id, category);
            return ResponseEntity.ok(updatedCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        boolean deleted = categoryService.deleteCategory(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Category>> searchCategories(@RequestParam(required = false) String search_query) {
        if (search_query != null && !search_query.isEmpty()) {
            List<Category> searchResults = new ArrayList<>();
            try {
                Long id = Long.parseLong(search_query);
                Optional<Category> categoryById = categoryService.getCategoryById(id);
                categoryById.ifPresent(searchResults::add);
            } catch (NumberFormatException e) {
            }

            List<Category> categoriesByName = categoryService.getCategoriesByName(search_query);
            searchResults.addAll(categoriesByName);

            if (!searchResults.isEmpty()) {
                return ResponseEntity.ok(searchResults);
            } else {
                /* Return empty list in case of non coincidences */
                return ResponseEntity.ok(Collections.emptyList());
            }
        }

        /* Return all the products in case of no search_query */
        List<Category> allCategories = categoryService.getAllCategories();
        return ResponseEntity.ok(allCategories);
    }
}
