package com.techstart.dto;

import lombok.Data;

@Data
public class DtoReportProduct {

    private String productCode;
    private String description;
    private String distributorData;
    private String manufacturerData;

    public DtoReportProduct(String productCode, String description) {
        this.productCode = productCode;
        this.description = description;
    }
}