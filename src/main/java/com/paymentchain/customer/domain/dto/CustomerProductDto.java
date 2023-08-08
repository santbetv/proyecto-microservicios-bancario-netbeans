/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.customer.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author rizzoli
 */
@Schema(name = "CustomerProductDtoRequest",  description = "Model represent the domian CustomerProduct on database")
@Getter
@Setter
public class CustomerProductDto {
    
    @Schema(name = "productId", required = true, example = "1", defaultValue = "prueba", description = "productId de customer")
    private Long productId;
    @Schema(name = "productName", required = true, example = "TC", defaultValue = "prueba", description = "productName de customer")
    private String productName;
}
