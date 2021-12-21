package com.techstart.dto;

import lombok.Data;
/*
 * Author: Renso Valencia Ventura
 * Date: 21/12/2021
 * Version: 1.0
 */
@Data
public class DtoInvoice {

    private String invoiceNumber;
    private String purchaseDate;
    private Double totalPurchase;
    private String codeCustomer;
    private String codeDistributor;
    private String CustomerId;
    private String DistributorId;

    public DtoInvoice(String invoiceNumber, String purchaseDate, Double totalPurchase, String codeCustomer, String codeDistributor) {
        this.invoiceNumber = invoiceNumber;
        this.purchaseDate = purchaseDate;
        this.totalPurchase = totalPurchase;
        this.codeCustomer = codeCustomer;
        this.codeDistributor = codeDistributor;
    }
}
