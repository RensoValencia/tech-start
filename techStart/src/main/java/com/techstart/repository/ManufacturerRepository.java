package com.techstart.repository;

import com.techstart.model.Manufacturer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
/*
 * Author: Renso Valencia Ventura
 * Date: 21/12/2021
 * Version: 1.0
 */
@Repository
public interface ManufacturerRepository extends MongoRepository<Manufacturer, String> {

    @Query("select m from Manufacturer where m.name = ?1")
    Manufacturer findByName(String name);
}
