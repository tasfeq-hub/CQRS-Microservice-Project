package com.app.cqrs.ProductService.rest.controller;

import com.app.cqrs.ProductService.command.CreateProductCommand;
import com.app.cqrs.ProductService.rest.model.ProductRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final Environment env;
    private final CommandGateway commandGateway;

    @Autowired
    public ProductController(Environment env, CommandGateway commandGateway) {
        this.env = env;
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createProduct(@RequestBody ProductRestModel model) {
        String result;

        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .productId(UUID.randomUUID().toString())
                .price(model.getPrice())
                .title(model.getTitle())
                .quantity(model.getQuantity())
                .build();

        try {
            result = commandGateway.sendAndWait(createProductCommand);
        } catch (Exception e) {
            result = e.getLocalizedMessage();
        }

        return result;
    }

    @GetMapping
    public String getProduct() {
        return "HTTP GET Handled" + env.getProperty("local.server.port");
    }

    @PutMapping
    public String updateProduct() {
        return "HTTP PUT Handled";
    }

    @DeleteMapping
    public String deleteProduct() {
        return "HTTP DELETE Handled";
    }

}
