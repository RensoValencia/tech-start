package com.techstart.dto;

import lombok.Data;
/*
 * Author: Renso Valencia Ventura
 * Date: 21/12/2021
 * Version: 1.0
 */
@Data
public class DtoDistributor {

    private String name;
    private String address;
    private String contact;

    public DtoDistributor(String name, String address, String contact) {
        this.name = name;
        this.address = address;
        this.contact = contact;
    }
}