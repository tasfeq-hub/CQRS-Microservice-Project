package com.app.cqrs.CoreService.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class ReserveProductCommand {

    @TargetAggregateIdentifier
    private String productId;

    private String orderId;

    private String userid;

    private int quantity;
}
