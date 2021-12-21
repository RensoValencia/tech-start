package com.techstart.dto;

import lombok.Data;

/*
 * Author: Renso Valencia Ventura
 * Date: 21/12/2021
 * Version: 1.0
 */
@Data
public class DtoInvoiceLine {

    public Double quantity;
    public Double weight;
    public String unitOfMeasure;
    public Double unitPrice;
    public String codeProductId;
    public String codeInvoiceId;

    public DtoInvoiceLine(Double quantity, Double weight, String unitOfMeasure, Double unitPrice, String codeProductId, String codeInvoiceId) {
        this.quantity = quantity;
        this.weight = weight;
        this.unitOfMeasure = unitOfMeasure;
        this.unitPrice = unitPrice;
        this.codeProductId = codeProductId;
        this.codeInvoiceId = codeInvoiceId;
    }
}
