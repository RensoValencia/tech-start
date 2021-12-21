package com.techstart.dto;

import lombok.Data;
/*
 * Author: Renso Valencia Ventura
 * Date: 21/12/2021
 * Version: 1.0
 */
@Data
public class DtoArticle {

    private String codeManufacturer;
    private String codeDistributor;
    private String description;
    private String productCode;
    private String manufacturerId;

    public DtoArticle(String productCode, String description,
                      String codeManufacturer, String codeDistributor) {
        this.productCode = productCode;
        this.description = description;
        this.codeManufacturer = codeManufacturer;
        this.codeDistributor = codeDistributor;
    }
}