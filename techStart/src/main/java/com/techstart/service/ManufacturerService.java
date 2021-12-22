package com.techstart.service;

import com.techstart.repository.ManufacturerRepository;
import com.techstart.model.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/*
 * Author: Renso Valencia Ventura
 * Date: 21/12/2021
 * Version: 1.0
 */
@Service
public class ManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public void create(Manufacturer customer)  {
        manufacturerRepository.save(customer);
    }

    public Manufacturer findByName(String name)  {
        return manufacturerRepository.findByName(name);
    }

    public List<Manufacturer> findAll()  {
        return manufacturerRepository.findAll();
    }

    public Manufacturer findById(String id)  {
        return manufacturerRepository.findById(id).get();
    }
}