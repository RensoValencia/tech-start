package com.techstart.controller;

import com.techstart.dto.DtoReportProduct;
import com.techstart.model.Article;
import com.techstart.model.Customer;
import com.techstart.model.Distributor;
import com.techstart.model.Invoice;
import com.techstart.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired private ManufacturerService manufacturerService;
    @Autowired private InvoiceService invoiceService;
    @Autowired private DistributorService distributorService;
    @Autowired private CustomerService customerService;
    @Autowired private ArticleService articleService;

    @GetMapping("/by-invoice")
    public List<Invoice> byInvoice() {

        List<Invoice> listInvoice = new ArrayList<>();

         Map<String, Invoice> mapInvoice = invoiceService.findAll()
                .stream()
                .collect(
                        Collectors.toMap(Invoice::getInvoiceNumber, Function.identity(),
                                (i1, i2) ->
                                        new Invoice(i1.getInvoiceNumber(),
                                        i1.getPurchaseDate(),
                                        i1.getTotalPurchase() + i2.getTotalPurchase())))
                 ;

        mapInvoice.forEach((k, v) ->
                {
                    listInvoice.add(v);
                }
                );
        return  listInvoice;
    }

    @GetMapping("/by-distributor")
    public List<Distributor> byDistributor() {

        List<Distributor> listDistributor = new ArrayList<>();

        Map<String, Distributor> mapDistributor = distributorService.findAll()
                .stream()
                .collect(
                        Collectors.toMap(Distributor::getName, Function.identity(),
                                (d1, d2) ->
                                {
                                        Distributor distributor = new Distributor(d1.getName(),
                                                d1.getAddress(),
                                                d1.getContact());
                                        distributor.setGroupName(d1.getName() + ", " + d2.getName());
                                        return distributor;
                                }
                        ));

        mapDistributor.forEach((k, v) ->
                {
                    listDistributor.add(v);
                }
        );
        return listDistributor;
    }

    @GetMapping("/by-customer-location")
    public List<Customer> byCustomer() {

        List<Customer> listCustomer = new ArrayList<>();

        Map<String, Customer> mapCustomer = customerService.findAll()
                .stream()
                .collect(
                        Collectors.toMap(Customer::getName, Function.identity(),
                                (d1, d2) ->
                                {
                                    Customer distributor = new Customer(d1.getName(),
                                            d1.getAddress(),
                                            d1.getContact());
                                    distributor.setGroupName(d1.getName() + ", " + d2.getName());
                                    return distributor;
                                }
                        ));

        mapCustomer.forEach((k, v) ->
                {
                    listCustomer.add(v);
                }
        );
        return listCustomer;
    }

    @GetMapping("/by-product")
    public List<DtoReportProduct> byProduct() {

        return articleService.findAll()
                .stream()
                .map( (n) ->
                        {
                            DtoReportProduct dtoReport = new DtoReportProduct(
                                    n.getProductCode(), n.getDescription());
                            dtoReport.setDistributorData(manufacturerService.findById(n.getManufacturerId()).toString());
                            dtoReport.setManufacturerData(distributorService.findById(n.getDistributorId()).toString());
                            return dtoReport;
                        }
                )
                .collect(Collectors.toList());


    }
}