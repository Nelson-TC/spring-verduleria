package com.example.springverduleria.controller;

import com.example.springverduleria.exception.ErrorFormatter;
import com.example.springverduleria.model.Invoice;
import com.example.springverduleria.model.Sale;
import com.example.springverduleria.service.InvoiceService;
import com.example.springverduleria.service.SaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final SaleService saleService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, SaleService saleService) {
        this.invoiceService = invoiceService;
        this.saleService = saleService;
    }

    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/by-sale-id/{id}")
    public ResponseEntity<?> getInvoiceBySaleId(@PathVariable Long id) {
        Optional<Invoice> invoice = invoiceService.getInvoiceBySaleId(id);
        if (invoice.isPresent()) {
            return ResponseEntity.ok(invoice.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInvoiceById(@PathVariable Long id) {
        Optional<Invoice> invoice = invoiceService.getInvoiceById(id);
        if (invoice.isPresent()) {
            return ResponseEntity.ok(invoice.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        boolean deleted = invoiceService.deleteInvoice(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/create/{saleId}")
    public ResponseEntity<?> createInvoice(@Valid @RequestBody Invoice invoice, BindingResult bindingResult, @PathVariable Long saleId) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> errorBody = ErrorFormatter.formatValidationErrors(bindingResult.getFieldErrors());
            return ResponseEntity.badRequest().body(errorBody);
        }

        Optional<Sale> sale = saleService.getSaleById(saleId);
        if (!sale.isPresent()) {
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("message", "La venta con ID " + saleId + " no fue encontrada.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
        }

        if (sale.get().getInvoice() != null) {
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("message", "La venta con ID " + saleId + " ya cuenta con una factura.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
        }

        Date currentDate = new Date();
        invoice.setDate(currentDate);
        invoice.setSale(sale.get());
        Invoice newInvoice = invoiceService.createInvoice(invoice);
        return ResponseEntity.status(HttpStatus.CREATED).body(newInvoice);
    }


}
