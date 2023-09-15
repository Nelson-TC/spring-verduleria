package com.example.springverduleria.model;
import com.example.springverduleria.converters.JsonNodeConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "sales")
@Setter
@Getter
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private double total;
    @Convert(converter = JsonNodeConverter.class)
    private JsonNode content;
    @OneToOne(mappedBy = "sale", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Invoice invoice;

}
