package com.app.cqrs.ProductService.rest.command.controller;

import com.app.cqrs.ProductService.rest.command.CreateProductCommand;
import com.app.cqrs.ProductService.rest.command.model.ProductRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductCommandController {

    private final Environment env;
    private final CommandGateway commandGateway;

    @Autowired
    public ProductCommandController(Environment env, CommandGateway commandGateway) {
        this.env = env;
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createProduct(@Valid @RequestBody ProductRestModel model) {
        String result;

        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .productId(UUID.randomUUID().toString())
                .price(model.getPrice())
                .title(model.getTitle())
                .quantity(model.getQuantity())
                .build();

        result = commandGateway.sendAndWait(createProductCommand);

        return result;
    }
}
