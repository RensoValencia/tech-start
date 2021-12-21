package com.techstart.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/*
 * Author: Renso Valencia Ventura
 * Date: 21/12/2021
 * Version: 1.0
 */
@Document(collection = "invoiceline")
@Data
public class InvoiceLine {

    @Id
    private Double quantity;
    private Double weight;
    private String unitOfMeasure;
    private Double unitPrice;

    private String productId;
    private String invoiceId;

    public InvoiceLine(Double quantity, Double weight, String unitOfMeasure, Double unitPrice) {
        this.quantity = quantity;
        this.weight = weight;
        this.unitOfMeasure = unitOfMeasure;
        this.unitPrice = unitPrice;
    }
}