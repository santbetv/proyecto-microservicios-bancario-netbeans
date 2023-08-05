package com.paymentchain.customer.common;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Santiago Betancur
 */
@Getter
@Setter
public class StandarizeApiExeceptionResponse {
        @Schema(description = "The unique uri identifier that categorizes the error", name = "type", required = false, example = "/errors/authentication/not-authorized")
        private String type = "/errors/uncategorized";
	
        @Schema(description = "A brief, human-readable message about the error", name = "title", required = false, example = "The user does not have autorization")
	private String title;
	
        @Schema(description = "The unique error code", name = "code", required = false, example = "192")
	private String code;
	
        @Schema(description = "A human-readable explanation of the error", name = "detail", required = false, example = "The user does not have the propertly persmissions to acces the resource, please contact with ass https://prueba.com")
	private String detail;
	
        @Schema(description = "A URI that identifies the specific occurrence of the error", name = "detail", required = false, example = "/errors/authentication/not-authorized/01")
	private String instance = "/errors/uncategorized/bank";

    public StandarizeApiExeceptionResponse(String title, String code, String detail) {
        super();
        this.title = title;
        this.code = code;
        this.detail = detail;
    }
}
