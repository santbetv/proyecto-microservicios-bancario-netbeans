/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.customer.repository;

import com.paymentchain.customer.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author rizzoli
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    
}
