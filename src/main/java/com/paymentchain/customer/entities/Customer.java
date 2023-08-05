package com.paymentchain.customer.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Santiago Betancur
 */

@Getter
@Setter
@ToString(includeFieldNames=true)
@Entity
@Schema(name = "Customer",  description = "Model represent a Customer on database")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Schema(name = "code", required = true, example = "01", defaultValue = "01", description = "Code de customer")
    private String code;
    @Schema(name = "name", required = true, example = "Tomas", defaultValue = "prueba", description = "Name de customer")
    private String name;
    @Schema(name = "phone", required = true, example = "3165106361", defaultValue = "prueba", description = "phone de customer")
    private String phone;
    @Schema(name = "iban", required = true, example = "000251487", defaultValue = "prueba", description = "iban de customer")
    private String iban;
    @Schema(name = "surname", required = true, example = "Quintero", defaultValue = "prueba", description = "surname de customer")
    private String surname;
    @Schema(name = "address", required = true, example = "Calle 16 15 # 14", defaultValue = "prueba", description = "surname de customer")
    private String address;
    
    @Schema(name = "products", required = false, example = "[{\"productId\": 1}]", defaultValue = "[{\"productId\": 1}]", description = "products de customer")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerProduct> products;
    
    @Schema(name = "transactions", required = false, example = "[{}]", defaultValue = "[{}]", description = "surname de customer")
    @Transient
    private List<?> transactions;
}
