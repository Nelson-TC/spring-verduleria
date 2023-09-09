package com.example.springverduleria.model;
import com.example.springverduleria.converters.JsonNodeConverter;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;

@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private double total;
    @Convert(converter = JsonNodeConverter.class)
    private JsonNode content;
    @Column(name = "client_id")
    private Long clientId;
    @ManyToOne
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private Client client;



}
