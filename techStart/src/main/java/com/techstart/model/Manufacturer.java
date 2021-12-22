package com.techstart.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/*
 * Author: Renso Valencia Ventura
 * Date: 21/12/2021
 * Version: 1.0
 */
@Document(collection = "manufacturer")
@Data
public class Manufacturer {

    @Id
    private String id;
    private String name;
    private String address;
    private String contact;

    public Manufacturer(String name, String address, String contact) {
        this.name = name;
        this.address = address;
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "name: " + name + " - address: " + address;
    }
}