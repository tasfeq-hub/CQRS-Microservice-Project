package com.app.cqrs.ProductService.rest.command;

import com.app.cqrs.CoreService.command.ReserveProductCommand;
import com.app.cqrs.CoreService.event.ProductReservedEvent;
import com.app.cqrs.ProductService.rest.query.event.ProductCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;

    public ProductAggregate() { }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) {
        if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("price can not be less than or equal to zero!");
        }

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
        BeanUtils.copyProperties(createProductCommand,productCreatedEvent);

        AggregateLifecycle.apply(productCreatedEvent);
    }

    @CommandHandler
    public void handle(ReserveProductCommand reserveProductCommand) {

        if(quantity > reserveProductCommand.getQuantity()) {
            throw new IllegalArgumentException("Insufficient amount of item");
        }

        ProductReservedEvent productReservedEvent = ProductReservedEvent.builder()
                .productId(reserveProductCommand.getProductId())
                .quantity(reserveProductCommand.getQuantity())
                .orderId(reserveProductCommand.getOrderId())
                .userid(reserveProductCommand.getUserid())
                .build();

        AggregateLifecycle.apply(productReservedEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent){
        this.productId= productCreatedEvent.getProductId();
        this.title = productCreatedEvent.getTitle();
        this.price = productCreatedEvent.getPrice();
        this.quantity = productCreatedEvent.getQuantity();
    }

    @EventSourcingHandler
    public void on(ProductReservedEvent productReservedEvent){
        this.quantity -= productReservedEvent.getQuantity();
    }
}
