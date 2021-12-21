package com.techstart.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/*
 * Author: Renso Valencia Ventura
 * Date: 21/12/2021
 * Version: 1.0
 */
@Document(collection = "product")
@Data
public class Article {
    @Id
    private String id;
    private String description;
    private String productCode;
    private String manufacturerId;
    private String distributorId;

    public Article(String productCode, String description, String manufacturerId) {
        this.productCode = productCode;
        this.description = description;
        this.manufacturerId = manufacturerId;
    }
}