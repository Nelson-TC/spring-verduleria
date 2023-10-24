package com.example.springverduleria.controller;

import com.example.springverduleria.controller.models.SaleOperation;
import com.example.springverduleria.controller.models.SaleRequest;
import com.example.springverduleria.model.Invoice;
import com.example.springverduleria.model.Product;
import com.example.springverduleria.model.Sale;
import com.example.springverduleria.service.InvoiceService;
import com.example.springverduleria.service.ProductService;
import com.example.springverduleria.service.SaleService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/sales")
public class SaleController {
    private final ProductService productService;
    private final SaleService saleService;
    private final InvoiceService invoiceService;

    @Autowired
    public SaleController(ProductService productService, SaleService saleService, InvoiceService invoiceService) {
        this.productService = productService;
        this.saleService = saleService;
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public List<Sale> getAllSales() {
        return saleService.getAllSales();
    }

    @GetMapping("/by-invoice-id/{invoiceId}")
    public ResponseEntity<?> getSaleByInvoice(@PathVariable Long invoiceId) {
        Optional<Invoice> invoice = invoiceService.getInvoiceById(invoiceId);

        if (invoice.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("La factura con ID " + invoiceId + " no fue encontrada.");
        }

        Sale sale = invoice.get().getSale();

        if (sale == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró una venta asociada a la factura con ID " + invoiceId);
        }

        return ResponseEntity.ok(mapSaleToDTO(sale));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSaleById(@PathVariable Long id) {
        Optional<Sale> sale = saleService.getSaleById(id);
        if (sale.isPresent()) {
            return ResponseEntity.ok(sale.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        boolean deleted = saleService.deleteSale(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSale(@RequestBody SaleRequest request) {
        List<String> errors = new ArrayList<>();

        for (SaleOperation operation : request.getContent()) {
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
            Date currentDate = new Date();
            double total = 0.0;

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
            ArrayNode contentArray = jsonNodeFactory.arrayNode();

            for (SaleOperation operation : request.getContent()) {
                Product product = productService.getProductById(operation.getId()).get();
                product.setStock(product.getStock() - operation.getQuantity());
                productService.updateProduct(operation.getId(), product);
                double subtotal = product.getUnitPrice() * operation.getQuantity();
                total += subtotal;

                JsonNode contentItem = objectMapper.valueToTree(operation);
                contentArray.add(contentItem);
            }

            JsonNode contentJsonNode = contentArray;
            DecimalFormat df = new DecimalFormat("#.##");
            total = Double.parseDouble(df.format(total));

            Sale sale = new Sale();
            sale.setContent(contentJsonNode);
            sale.setDate(currentDate);
            sale.setTotal(total);
            Sale createdSale =  saleService.createSale(sale);
            return ResponseEntity.ok(createdSale);
        }
    }

    private Sale mapSaleToDTO(Sale sale) {
        Sale saleDTO = new Sale();
        saleDTO.setId(sale.getId());
        saleDTO.setContent(sale.getContent());
        saleDTO.setTotal(sale.getTotal());
        saleDTO.setDate(sale.getDate());
        saleDTO.setInvoice(sale.getInvoice());
        return saleDTO;
    }

}

