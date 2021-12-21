package com.techstart.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/*
 * Author: Renso Valencia Ventura
 * Date: 21/12/2021
 * Version: 1.0
 */
@Document(collection = "distributor")
@Data
public class Distributor {

    @Id
    private String id;
    private String name;
    private String address;
    private String contact;

    public Distributor(String name, String address, String contact) {
        this.name = name;
        this.address = address;
        this.contact = contact;
    }
}
