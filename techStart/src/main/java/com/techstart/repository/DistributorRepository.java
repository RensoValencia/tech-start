package com.techstart.repository;

import com.techstart.model.Distributor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
/*
 * Author: Renso Valencia Ventura
 * Date: 21/12/2021
 * Version: 1.0
 */
@Repository
public interface DistributorRepository extends MongoRepository<Distributor, String> {
}
