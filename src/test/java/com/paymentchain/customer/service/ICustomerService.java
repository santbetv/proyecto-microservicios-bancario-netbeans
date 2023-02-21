/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.customer.service;

import com.paymentchain.customer.entities.Customer;
import java.util.List;

/**
 *
 * @author Santiago Betancur
 */
public interface ICustomerService {
    
    public Customer save(Customer customer);
    public List<Customer> findAll();

}
