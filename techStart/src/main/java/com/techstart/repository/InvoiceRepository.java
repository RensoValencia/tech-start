package com.techstart.repository;

import com.techstart.model.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * Author: Renso Valencia Ventura
 * Date: 21/12/2021
 * Version: 1.0
 */
@Repository
public interface InvoiceRepository extends MongoRepository<Invoice, String> {

    @Query("select i from Invoice")
    List<Invoice> getListInvoice();
}
