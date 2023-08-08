/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.customer.application.mapper;

import com.paymentchain.customer.domain.dto.CustomerDtoResponse;
import com.paymentchain.customer.domain.entities.Customer;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 *
 * @author rizzoli
 */
@Mapper(componentModel = "spring")
public interface CustomerResponseMapper {
    
    //fuente CustomerDtoRequest destino Customer
    @Mappings({
        @Mapping(source = "surName", target = "surname")
    })
    Customer toCustomer(CustomerDtoResponse c);
    List<Customer> toListCustomer(List<CustomerDtoResponse> customers);

    //fuente Customer destino customerDtoRequest
    @InheritInverseConfiguration
    @Mappings({
        @Mapping(source = "surname", target = "surName")
    })
    CustomerDtoResponse toCustomerDtoRequest(Customer c);

    @InheritInverseConfiguration
    List<CustomerDtoResponse> toListCustomerDtoRequest(List<Customer> customers);
    
}
