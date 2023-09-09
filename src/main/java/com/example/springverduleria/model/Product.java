package com.example.springverduleria.model;

import com.example.springverduleria.validation.ValidCategoryId;
import com.example.springverduleria.validation.ValidPrice;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    @NotNull(message = "El nombre es obligatorio")
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 75, message = "El nombre no puede superar los 75 carácteres")
    private String name;
    @NotNull(message = "El inventario es obligatorio")
    @Min(value = 0, message = "El inventario no puede ser negativo")
    private int stock;
    @NotNull(message = "El precio es obligatorio")
    @ValidPrice(message = "El precio debe ser un número valido con un maximo de dos decimales")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser negativo")
    private double unitPrice;
    @NotNull(message = "La unidad de medida es obligatoria")
    @NotBlank(message = "La unidad de medida es obligatoria")
    private String unitMeasurement;

    @Column(name = "category_id")
    /*@ValidCategoryId(message = "El ID de la categoria no es valida")*/
    private Long categoryId;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;

    @ManyToMany(mappedBy = "products")
    private Set<Batch> batches = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnitMeasurement() {
        return unitMeasurement;
    }

    public void setUnitMeasurement(String unitMeasurement) {
        this.unitMeasurement = unitMeasurement;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
        if (category != null) {
            this.categoryId = category.getId();
        }
    }

    public Set<Batch> getBatches() {
        return batches;
    }

    public void setBatches(Set<Batch> batches) {
        this.batches = batches;
    }
}
