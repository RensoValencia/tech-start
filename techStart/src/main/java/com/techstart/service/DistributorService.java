package com.techstart.service;

import com.techstart.model.Distributor;
import com.techstart.repository.DistributorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/*
 * Author: Renso Valencia Ventura
 * Date: 21/12/2021
 * Version: 1.0
 */
@Service
public class DistributorService {

    @Autowired
    private DistributorRepository distributorRepository;

    public void create(Distributor distributor)  {
        distributorRepository.save(distributor);
    }

    public List<Distributor> findAll()  {
        return distributorRepository.findAll();
    }

    public Distributor findById(String id)  {
        return distributorRepository.findById(id).get();
    }
}