package com.techstart.controller;

import com.techstart.dto.*;
import com.techstart.model.*;
import com.techstart.service.*;
import com.techstart.utils.Constant;
import org.apache.poi.openxml4j.opc.Package;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Collectors;
/*
 * Author: Renso Valencia Ventura
 * Date: 21/12/2021
 * Version: 1.0
 */
@RestController
@RequestMapping("/migration")
public class MigrationController {

    final Logger logger = LoggerFactory.getLogger(MigrationController.class);

    @Autowired private DistributorService distributorService;
    @Autowired private CustomerService customerService;
    @Autowired private ManufacturerService manufacturerService;
    @Autowired private ArticleService articleService;
    @Autowired private InvoiceService invoiceService;
    @Autowired private InvoiceLineService invoiceLineService;

    @GetMapping("/generate")
    public void generate() {

        try {
            FileInputStream file = new FileInputStream(new File(Constant.PATH_FILE));
            XSSFWorkbook myWorkBook = new XSSFWorkbook (Package.open(file));
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);

            int numRows = mySheet.getLastRowNum();
            List<DtoDistributor> listDistributor = new ArrayList<>();
            List<DtoCustomer> listCustomer = new ArrayList<>();
            List<DtoManufacturer> listManufacturer = new ArrayList<>();
            List<DtoArticle> listArticle = new ArrayList<>();
            List<DtoInvoice> listInvoice = new ArrayList<>();
            List<DtoInvoiceLine> listInvoiceLine = new ArrayList<>();

            for(int i = 1; i <= numRows; i++) {
                Row row = mySheet.getRow(i);
                listCustomer.add(new DtoCustomer(row.getCell(4).toString(),
                        row.getCell(5).toString(), Constant.DEFAULT_INFO));
                listDistributor.add(new DtoDistributor(row.getCell(2).toString(), row.getCell(3).toString(), Constant.DEFAULT_INFO));
                listManufacturer.add(new DtoManufacturer(row.getCell(6).toString(), row.getCell(7).toString(), Constant.DEFAULT_INFO));
                listArticle.add(new DtoArticle(
                        row.getCell(8).toString(),
                        row.getCell(9).toString(),
                        row.getCell(6).toString() + Constant.SEPARATOR + row.getCell(8).toString(),
                        row.getCell(2).toString() + Constant.SEPARATOR + row.getCell(8).toString()));

                listInvoice.add(new DtoInvoice(row.getCell(0).toString(),
                        row.getCell(1).toString(),
                        Double.parseDouble(row.getCell(14).toString()),
                        row.getCell(4).toString()
                                + Constant.SEPARATOR + row.getCell(0).toString(),
                        row.getCell(2).toString()
                                + Constant.SEPARATOR + row.getCell(0).toString()
                        ));
                listInvoiceLine.add(new DtoInvoiceLine(
                        Double.parseDouble((row.getCell(11) != null ? row.getCell(11).toString(): "0")),
                        Double.parseDouble(row.getCell(12).toString()),
                        row.getCell(10).toString(),
                        Double.parseDouble(row.getCell(13).toString()),
                        row.getCell(8).toString() + Constant.SEPARATOR + row.getCell(10).toString(),
                        row.getCell(0).toString() + Constant.SEPARATOR + row.getCell(10).toString()));
            }
            migrateDistributors(listDistributor);
            migrateCustomers(listCustomer);
            migrateManufacturer(listManufacturer);
            migrateArticles(listArticle);
            migrateInvoices(listInvoice);
            migrateInvoiceLines(listInvoiceLine);

        } catch (Exception e) {
            logger.error("error in the system: " + e.getMessage());
        }
    }

    private void migrateInvoiceLines(List<DtoInvoiceLine> listInvoiceLine) {
        Map<String, DtoInvoiceLine> invoiceLineCollections = listInvoiceLine.stream().collect(
                Collectors.toMap(DtoInvoiceLine::getUnitOfMeasure, Function.identity(),
                        (i1, i2) -> new DtoInvoiceLine(
                                i1.getQuantity(),
                                i1.getWeight(),
                                i1.getUnitOfMeasure(),
                                i1.getUnitPrice(),
                                i1.getCodeProductId(),
                                i1.getCodeInvoiceId()
                                )));

        invoiceLineCollections.forEach((k, v) -> {
            InvoiceLine invoiceLine = new InvoiceLine(v.getQuantity(),
                    v.getWeight(), v.getUnitOfMeasure(), v.getUnitPrice());

            String searchByNameProduct = v.getCodeProductId().split(Constant.SEARCH_PATTERN)[0];
            String searchByNameInvoice = v.getCodeInvoiceId().split(Constant.SEARCH_PATTERN)[0];

            Article articleData = getByNameProduct(searchByNameProduct);
            Invoice distributorData = getByNameInvoice(searchByNameInvoice);

            invoiceLine.setProductId(articleData.getId());
            invoiceLine.setInvoiceId(distributorData.getId());
            invoiceLineService.create(invoiceLine);
        });
    }

    private void migrateInvoices(List<DtoInvoice> listInvoice) {
        Map<String, DtoInvoice> invoiceCollections = listInvoice.stream().collect(
                Collectors.toMap(DtoInvoice::getCodeDistributor, Function.identity(),
                        (i1, i2) -> new DtoInvoice(
                                i1.getInvoiceNumber(),
                                i1.getPurchaseDate(),
                                i1.getTotalPurchase() + i2.getTotalPurchase(),
                                i1.getCodeCustomer(),
                                i1.getCodeDistributor())));

        invoiceCollections.forEach((k, v) -> {
            Invoice invoice = new Invoice(v.getInvoiceNumber(), v.getPurchaseDate(), v.getTotalPurchase());

            String searchByNameCustomer = v.getCodeCustomer().split(Constant.SEARCH_PATTERN)[0];
            String searchByNameDistributor = v.getCodeDistributor().split(Constant.SEARCH_PATTERN)[0];

            Customer customerData = getByNameCustomer(searchByNameCustomer);
            Distributor distributorData = getByNameDistributor(searchByNameDistributor);

            invoice.setCustomerId(customerData.getId());
            invoice.setDistributorId(distributorData.getId());
            invoiceService.create(invoice);
        });
    }

    private void migrateArticles(List<DtoArticle> listArticle) {
        Map<String, DtoArticle> productCollections = listArticle.stream().collect(
                Collectors.toMap(DtoArticle::getCodeManufacturer, Function.identity(),
                        (p1, p2) -> new DtoArticle(
                                p1.getProductCode(),
                                p1.getDescription(),
                                p1.getCodeManufacturer(), p1.getCodeDistributor())));

        productCollections.forEach((k, v) -> {
            Article article = new Article(v.getProductCode(), v.getDescription(), v.getCodeManufacturer());

            String searchByNameManufacturer = v.getCodeManufacturer().split(Constant.SEARCH_PATTERN)[0];
            String searchByNameDistributor = v.getCodeDistributor().split(Constant.SEARCH_PATTERN)[0];

            Manufacturer manufacturer = getByNameManufacturer(searchByNameManufacturer);
            Distributor distributor = getByNameDistributor(searchByNameDistributor);

            article.setManufacturerId(manufacturer.getId());
            article.setDistributorId(distributor.getId());
            articleService.create(article);
        });
    }

    private void migrateManufacturer(List<DtoManufacturer> listManufacturer) {
        Map<String, DtoManufacturer> manufacturerCollections = listManufacturer.stream().collect(
                Collectors.toMap(DtoManufacturer::getName, Function.identity(),
                        (m1, m2) -> new DtoManufacturer(m1.getName(), m1.getAddress(), m1.getContact())));

        manufacturerCollections.forEach((k, v) -> {
            manufacturerService.create(new Manufacturer(v.getName(), v.getAddress(), v.getContact()));
        });
    }

    private void migrateCustomers(List<DtoCustomer> listCustomer) {
        Map<String, DtoCustomer> customerCollections = listCustomer.stream().collect(
                Collectors.toMap(DtoCustomer::getName, Function.identity(),
                        (c1, c2) -> new DtoCustomer(c1.getName(), c1.getAddress(), c1.getContact())));

        customerCollections.forEach((k, v) -> {
            customerService.create(new Customer(v.getName(), v.getAddress(), v.getContact()));
        });
    }

    private void migrateDistributors(List<DtoDistributor> listDistributor) {
        Map<String, DtoDistributor> distributorCollections = listDistributor.stream().collect(
                Collectors.toMap(DtoDistributor::getName, Function.identity(),
                        (d1, d2) -> new DtoDistributor(d1.getName(), d1.getAddress(), d1.getContact())));
        distributorCollections.forEach((k, v) -> {
            distributorService.create(new Distributor(v.getName(), v.getAddress(), v.getContact()));
        });
    }

    private Manufacturer getByNameManufacturer(String name) {
        return manufacturerService.findAll()
                .stream()
                .filter(n -> n.getName().equals(name))
                .collect(Collectors.toList()).get(0);
    }

    private Distributor getByNameDistributor(String name) {
        return distributorService.findAll()
                .stream()
                .filter(n -> n.getName().equals(name))
                .collect(Collectors.toList()).get(0);
    }

    private Customer getByNameCustomer(String name) {
        return customerService.findAll()
                .stream()
                .filter(n -> n.getName().equals(name))
                .collect(Collectors.toList()).get(0);
    }

    private Article getByNameProduct(String name) {
        return articleService.findAll()
                .stream()
                .filter(n -> n.getProductCode().equals(name))
                .collect(Collectors.toList()).get(0);
    }

    private Invoice getByNameInvoice(String name) {
        return invoiceService.findAll()
                .stream()
                .filter(n -> n.getInvoiceNumber().equals(name))
                .collect(Collectors.toList()).get(0);
    }
}