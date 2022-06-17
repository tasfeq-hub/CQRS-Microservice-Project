package com.app.cqrs.CoreService.event;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class ProductReservedEvent {

    private String productId;

    private String orderId;

    private String userid;

    private int quantity;
}
