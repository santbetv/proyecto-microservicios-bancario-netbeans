/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.customer.application.service.impl;

import com.paymentchain.customer.infraestructure.client.ClientWebClient;
import com.paymentchain.customer.domain.entities.Customer;
import com.paymentchain.customer.domain.entities.CustomerProduct;
import com.paymentchain.customer.infraestructure.exception.BussinesRuleException;
import com.paymentchain.customer.domain.repository.CustomerRepository;
import com.paymentchain.customer.domain.service.ICustomerService;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Santiago Betancur
 */
@Service
public class CustomerService implements ICustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final ClientWebClient clientWebClient;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, ClientWebClient clientWebClient) {
        this.customerRepository = customerRepository;
        this.clientWebClient = clientWebClient;
    }

    @Value("${URL.PRODUCT}")
    private String urlProduct;

    @Override
    @Transactional() //
    public Customer save(Customer customer) throws BussinesRuleException, UnknownHostException {
        if (customer.getProducts() != null) {
            for (Iterator<CustomerProduct> it = customer.getProducts().iterator(); it.hasNext();) {
                CustomerProduct dto = it.next();
                String productName = clientWebClient.getProductName(dto.getProductId(), urlProduct, "name");
                if (productName.isBlank()) {
                    BussinesRuleException exception = new BussinesRuleException("1025", "Error de validacion, producto no existe", HttpStatus.PRECONDITION_FAILED);
                    throw exception;
                } else {
                    dto.setProductName(productName);
                    dto.setCustomer(customer);
                }
            }
        }
        Customer save = customerRepository.save(customer);
        return save;
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
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
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
    public Customer findByCode(String code) throws BussinesRuleException, UnknownHostException {
        Customer customer = clientWebClient.get(code);
        return customer;
    }

}
