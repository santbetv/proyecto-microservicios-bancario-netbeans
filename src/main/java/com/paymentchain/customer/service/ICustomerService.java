/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.customer.service;

import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.exception.BussinesRuleException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Santiago Betancur
 */
public interface ICustomerService {
    
    public Optional<Customer> findById(Long id);
    public List<Customer> findAll();
    public Customer findByCode(String code) throws BussinesRuleException, UnknownHostException;
    
    public Customer save(Customer customer) throws BussinesRuleException, UnknownHostException;
    public boolean put(Long id,Customer customer);
    public boolean delete(Long id);    


}
