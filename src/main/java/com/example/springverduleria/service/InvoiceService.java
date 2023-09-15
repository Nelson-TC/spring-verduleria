package com.example.springverduleria.service;

import com.example.springverduleria.model.Invoice;
import com.example.springverduleria.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<Invoice> getAllInvoices(){
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> getInvoiceById(Long id){
        return invoiceRepository.findById(id);
    }

    public Optional<Invoice> getInvoiceBySaleId(Long saleId){
        return invoiceRepository.findBySaleId(saleId);
    }

    public Invoice createInvoice(Invoice invoice){
        invoice.setId(-1L);
        return invoiceRepository.save(invoice);
    }

}
