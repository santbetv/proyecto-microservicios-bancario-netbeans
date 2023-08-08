/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.customer.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author rizzoli
 */
@Schema(name = "CustomerDtoRequest",  description = "Model represent the domian Customer on database")
@Getter
@Setter
public class CustomerDtoRequest {
    
    @Schema(name = "code", required = true, example = "01", defaultValue = "prueba", description = "code de customer")
    private String code;
    @Schema(name = "name", required = true, example = "Tomas", defaultValue = "prueba", description = "Name de customer")
    private String name;
    @Schema(name = "phone", required = true, example = "3165106361", defaultValue = "prueba", description = "phone de customer")
    private String phone;
    @Schema(name = "iban", required = true, example = "000251487", defaultValue = "prueba", description = "iban de customer")
    private String iban;
    @Schema(name = "surName", required = true, example = "Quintero", defaultValue = "prueba", description = "surname de customer")
    private String surName;
    @Schema(name = "address", required = true, example = "Calle 16 15 # 14", defaultValue = "prueba", description = "surname de customer")
    private String address;
    
    @Schema(name = "products", required = false, example = "[{\"productId\": 1}]", defaultValue = "[{\"productId\": 1}]", description = "products de customer")
    private List<CustomerProductDto> products;
    
}
