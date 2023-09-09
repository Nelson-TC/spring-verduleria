package com.example.springverduleria.model;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "batches")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entryDate;

    private String status;

    @Column(name = "purchase_id")
    private Long purchaseId;

    @ManyToOne
    @JoinColumn(name = "purchase_id", insertable = false, updatable = false)
    private Purchase purchase;

    @ManyToMany
    @JoinTable(name = "batch_product",
            joinColumns = @JoinColumn(name = "batch_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntryDate(){
        return entryDate;
    }

    public void setEntryDate(String entryDate){
        this.entryDate = entryDate;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public Long getPurchaseId(){
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId){
        this.purchaseId = purchaseId;
    }

    public Purchase getPurchase(){
        return purchase;
    }

    public void setPurchase(Purchase purchase){
        this.purchase = purchase;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        products.add(product);
        product.getBatches().add(this);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.getBatches().remove(this);
    }


}
