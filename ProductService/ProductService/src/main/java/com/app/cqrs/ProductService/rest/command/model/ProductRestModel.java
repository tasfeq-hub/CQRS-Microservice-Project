package com.app.cqrs.ProductService.rest.command.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class ProductRestModel {

    @NotBlank(message = "Title is a required field")
    private String title;

    @Min(value = 1, message = "Price must be greater than 1")
    private BigDecimal price;

    @Min(value = 1, message = "Quantity must be greater than 1")
    @Max(value = 5, message = "Quantity must be less than 6")
    private Integer quantity;

}
