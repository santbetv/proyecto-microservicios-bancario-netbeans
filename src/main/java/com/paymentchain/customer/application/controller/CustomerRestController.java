/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.customer.application.controller;

import com.paymentchain.customer.application.mapper.CustomerRequestMapper;
import com.paymentchain.customer.application.mapper.CustomerResponseMapper;
import com.paymentchain.customer.domain.dto.CustomerDtoRequest;
import com.paymentchain.customer.domain.dto.CustomerDtoResponse;
import com.paymentchain.customer.domain.entities.Customer;
import com.paymentchain.customer.infraestructure.exception.BussinesRuleException;
import com.paymentchain.customer.domain.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Customer API", description = "This API service all funcionality for management customer")
@RestController
@RequestMapping("/customer")
public class CustomerRestController {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerRestController.class);
    private final ICustomerService iCustomerService;
    private final CustomerResponseMapper  customerResponseMapper;
    private final CustomerRequestMapper customerRequestMapper   ;

    @Autowired
    public CustomerRestController(ICustomerService iCustomerService, CustomerResponseMapper customerResponseMapper, CustomerRequestMapper customerRequestMapper) {
        this.iCustomerService = iCustomerService;
        this.customerResponseMapper = customerResponseMapper;
        this.customerRequestMapper = customerRequestMapper;
    }

    @Operation(description = "Return all customer bunled into Response", summary = "Return 204 if no found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Exito"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping()
    public ResponseEntity<List<CustomerDtoResponse>> findAll() {
        List<Customer> find = iCustomerService.findAll();
        if (find == null || find.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<CustomerDtoResponse> data =customerResponseMapper.toListCustomerDtoRequest(find);
            return ResponseEntity.ok(data);
        }
    }

    
    
    @Operation(description = "Return for id customer bunled into Response", summary = "Return 204 if no found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Exito"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDtoResponse> get(@PathVariable Long id) {
        Optional<Customer> find = iCustomerService.findById(id);
        if (find.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(customerResponseMapper.toCustomerDtoRequest(find.get()));
        }
//        return find.map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(description = "Return full customer, product, transaction bunled into Response", summary = "Return 412 if product no found" + " Error de validacion, producto no existe" + " and trasaction in empty if not found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Exito"),
        @ApiResponse(responseCode = "503", description = "Service Unavailable")})

    @GetMapping("/full")
    public CustomerDtoResponse getByCode(@RequestParam String code) throws BussinesRuleException, UnknownHostException {
        Customer customer = iCustomerService.findByCode(code);
        return customerResponseMapper.toCustomerDtoRequest(customer);
    }
    
    @Operation(description = "Return put for id customer bunled into Response", summary = "Return 404 if no found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Exito"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable Long id, @RequestBody CustomerDtoRequest input) {
        if (iCustomerService.put(id, customerRequestMapper.toCustomer(input))) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    
    @Operation(description = "Return save customer bunled into Response", summary = "Return 412 if product no found" + " Error de validacion, producto no existe")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created"),
        @ApiResponse(responseCode = "503", description = "Service Unavailable")})
    @PostMapping
    public ResponseEntity<CustomerDtoResponse> post(@RequestBody CustomerDtoRequest input) throws BussinesRuleException, UnknownHostException {
        Customer save = iCustomerService.save(customerRequestMapper.toCustomer(input));
        return new ResponseEntity<>(customerResponseMapper.toCustomerDtoRequest(save), HttpStatus.CREATED);
    }

    @Operation(description = "Return delete for id customer bunled into Response", summary = "Return 404 if no found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Exito")})
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerDtoResponse> delete(@PathVariable Long id) {
        if (iCustomerService.delete(id)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    

}
