package com.techstart.service;

import com.techstart.model.InvoiceLine;
import com.techstart.repository.InvoiceLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/*
 * Author: Renso Valencia Ventura
 * Date: 21/12/2021
 * Version: 1.0
 */
@Service
public class InvoiceLineService {

    @Autowired
    private InvoiceLineRepository invoiceLineRepository;

    public void create(InvoiceLine invoiceLine) {
        invoiceLineRepository.save(invoiceLine);
    }

    public List<InvoiceLine> findAll()  {
        return invoiceLineRepository.findAll();
    }

}