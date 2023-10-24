package com.example.springverduleria.service;

import com.example.springverduleria.model.Sale;
import com.example.springverduleria.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleService {
    private final SaleRepository saleRepository;
    @Autowired
    public SaleService(SaleRepository saleRepository){
        this.saleRepository = saleRepository;
    }

    public List<Sale> getAllSales(){
        return saleRepository.findAll();
    }

    public boolean deleteSale(Long id){
        if(saleRepository.existsById(id)){
            saleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Sale> getSaleById(Long id){
        return saleRepository.findById(id);
    }
    public Sale createSale(Sale sale){
        sale.setId(-1L);
        return saleRepository.save(sale);
    }
}
