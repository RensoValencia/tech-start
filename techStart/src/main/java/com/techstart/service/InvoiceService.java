package com.techstart.service;

import com.techstart.model.Invoice;
import com.techstart.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/*
 * Author: Renso Valencia Ventura
 * Date: 21/12/2021
 * Version: 1.0
 */
@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public void create(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    public List<Invoice> findAll()  {
        return invoiceRepository.findAll();
    }

    public List<Invoice> getListInvoice()  {
        return invoiceRepository.getListInvoice();
    }
}