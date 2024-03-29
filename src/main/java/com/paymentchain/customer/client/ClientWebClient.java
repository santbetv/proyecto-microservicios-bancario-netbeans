/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.customer.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.entities.CustomerProduct;
import com.paymentchain.customer.repository.CustomerRepository;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

/**
 *
 * @author rizzoli
 */
@Component
public class ClientWebClient {

    private final WebClient.Builder webClientBuilder;
    CustomerRepository customerRepository;

    
    @Value("${URL.PRODUCT}")
    private String urlProduct;
    
    @Value("${URL.TRANSACTION}")
    private String urlTransaction;
    
    @Autowired
    public ClientWebClient(WebClient.Builder webClientBuilder, CustomerRepository customerRepository) {
        this.webClientBuilder = webClientBuilder;
        this.customerRepository = customerRepository;
    }
            

    //webClient requires HttpClient library to work propertly       
    HttpClient client = HttpClient.create()
            //Connection Timeout: is a period within which a connection between a client and a server must be established
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(EpollChannelOption.TCP_KEEPIDLE, 300)
            .option(EpollChannelOption.TCP_KEEPINTVL, 60)
            //Response Timeout: The maximun time we wait to receive a response after sending a request
            .responseTimeout(Duration.ofSeconds(1))
            // Read and Write Timeout: A read timeout occurs when no data was read within a certain 
            //period of time, while the write timeout when a write operation cannot finish at a specific time
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
            });

    

    public Customer get(String code) {
        Customer customer = customerRepository.findByCode(code);
        if (customer.getProducts() != null) {
            List<CustomerProduct> products = customer.getProducts();
            products.forEach(dto -> {
                try {
                    String productName = getProductName(dto.getProductId(), urlProduct, "name");
                    dto.setProductName(productName);
                } catch (Exception ex) {
                    Logger.getLogger("Fallo en customer client" + ex);
                }
            });
        }
        customer.setTransactions(getTransacctions(customer.getIban(), urlTransaction, "ibanAccount"));
        return customer;
    }

    private String getProductName(long id, String URL, String searchedAttribute) {
        WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                .baseUrl(URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", URL))
                .build();

        JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                .retrieve().bodyToMono(JsonNode.class).block();

        String name = block.get(searchedAttribute).asText();
        return name;
    }

    private <T> List<T> getTransacctions(String accountIban, String URL, String searchedAttribute) {
        List<T> trasnsactions = new ArrayList<>();
        try {
            WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                    .baseUrl(URL)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultUriVariables(Collections.singletonMap("url", URL))
                    .build();

            List<Object> block = build.method(HttpMethod.GET).uri(uriBuilder -> uriBuilder
                    .path("/transactions")
                    .queryParam(searchedAttribute, accountIban)
                    .build())
                    .retrieve().bodyToFlux(Object.class).collectList().block();
            trasnsactions = (List<T>) block;
        } catch (Exception e) {
            return trasnsactions;
        }
        return trasnsactions;
    }

}
