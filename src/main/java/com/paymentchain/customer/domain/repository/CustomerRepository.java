/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.customer.domain.repository;

import com.paymentchain.customer.domain.entities.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Santiago Betancur
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    public Optional<Customer> findByCode(String code);

}
