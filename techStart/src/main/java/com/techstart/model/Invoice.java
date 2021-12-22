package com.techstart.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
/*
 * Author: Renso Valencia Ventura
 * Date: 21/12/2021
 * Version: 1.0
 */
@Document(collection = "invoice")
@Data
public class Invoice {

    @Id
    private String id;
    private String invoiceNumber;
    private String purchaseDate;
    private Double totalPurchase;
    @Transient private String customerId;
    @Transient private String distributorId;

    public Invoice(String invoiceNumber, String purchaseDate, Double totalPurchase) {
        this.invoiceNumber = invoiceNumber;
        this.purchaseDate = purchaseDate;
        this.totalPurchase = totalPurchase;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Double getTotalPurchase() {
        return totalPurchase;
    }

    public void setTotalPurchase(Double totalPurchase) {
        this.totalPurchase = totalPurchase;
    }

    public String getCustomerId() {
        return customerId;
    }

    //public void setCustomerId(String customerId) {
       // this.customerId = customerId;
    //}

    public String getDistributorId() {
        return distributorId;
    }

    //public void setDistributorId(String distributorId) {
        //this.distributorId = distributorId;
    //}
}
