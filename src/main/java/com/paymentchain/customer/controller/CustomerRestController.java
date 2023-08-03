/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.customer.controller;

import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.exception.BussinesRuleException;
import com.paymentchain.customer.service.ICustomerService;
import java.net.UnknownHostException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Santiago Betancur
 */
@RestController
@RequestMapping("/customer")
public class CustomerRestController {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerRestController.class);
    private final ICustomerService iCustomerService;

    @Autowired
    public CustomerRestController(ICustomerService iCustomerService) {
        this.iCustomerService = iCustomerService;
    }
    
    @GetMapping()
    public ResponseEntity<List<Customer>> findAll() {
        List<Customer> find = iCustomerService.findAll();
        if (find== null || find.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(find);
        }   
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Customer> get(@PathVariable Long id) {
        Optional<Customer> find = iCustomerService.findById(id);
        return find.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable Long id, @RequestBody Customer input) {
        if (iCustomerService.put(id, input)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Customer> post(@RequestBody Customer input) throws BussinesRuleException, UnknownHostException{
        Customer save = iCustomerService.save(input);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> delete(@PathVariable Long id) {
        if (iCustomerService.delete(id)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/full")
    public Customer getByCode(@RequestParam String code) throws BussinesRuleException, UnknownHostException {
        Customer customer = iCustomerService.findByCode(code);
        return customer;
    }

}
