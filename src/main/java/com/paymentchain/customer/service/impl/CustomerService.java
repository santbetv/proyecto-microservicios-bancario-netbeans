/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.customer.service.impl;

import com.paymentchain.customer.client.ClientWebClient;
import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.entities.CustomerProduct;
import com.paymentchain.customer.repository.CustomerRepository;
import com.paymentchain.customer.service.ICustomerService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Santiago Betancur
 */
@Service
public class CustomerService implements ICustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);
    
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ClientWebClient clientWebClient;

    @Value("${URL.PRODUCT}")
    private String urlProduct;

    @Override
    @Transactional() //
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        try {
            Optional<Customer> customer = customerRepository.findById(id);
            if (!customer.isEmpty()) {
                customerRepository.delete(customer.get());
                return true;
            } else {
                return false;
            }
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true) //
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true) //
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public boolean put(Long id, Customer customer) {
        try {
            Optional<Customer> find = customerRepository.findById(id);
            if (!find.isEmpty()) {
                find.get().setCode(customer.getCode());
                find.get().setName(customer.getName());
                find.get().setIban(customer.getIban());
                find.get().setPhone(customer.getPhone());
                find.get().setSurname(customer.getSurname());
            } else {
                return false;
            }
            Customer save = customerRepository.save(find.get());
            customerRepository.save(save);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true) //
    public Customer findByCode(String code) {
        Customer customer = customerRepository.findByCode(code);
        List<CustomerProduct> products = customer.getProducts();

        //for each product find it name
        products.forEach(x -> {
            String productName = clientWebClient.extractByData(x.getId(), urlProduct, "name");
            x.setProductName(productName);
        });

        return customer;
    }

}
