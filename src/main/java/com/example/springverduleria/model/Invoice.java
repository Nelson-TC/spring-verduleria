package com.example.springverduleria.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "invoices")
@Setter
@Getter
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "El nombre es obligatorio")
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 75, message = "El nombre no puede superar los 75 carácteres")
    private String name;
    @NotNull(message = "La dirección es obligatoria")
    @NotBlank(message = "La dirección es obligatoria")
    private String address;
    @NotNull(message = "El nit es obligatorio")
    @NotBlank(message = "El nit es obligatorio")
    private String nit;
    private Date date;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", referencedColumnName = "id")
    @JsonIgnore
    private Sale sale;
}
