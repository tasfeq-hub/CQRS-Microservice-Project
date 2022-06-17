package com.appsdeveloperblog.estore.OrdersService.saga;

import com.app.cqrs.CoreService.command.ReserveProductCommand;
import com.app.cqrs.CoreService.event.ProductReservedEvent;
import com.appsdeveloperblog.estore.OrdersService.core.events.OrderCreatedEvent;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class OrderSaga {
	
	@Autowired
	private transient CommandGateway commandGateway;

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderSaga.class);

	@StartSaga
	@SagaEventHandler(associationProperty="orderId")
	public void handle(OrderCreatedEvent orderCreatedEvent) {
		ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
				.orderId(orderCreatedEvent.getOrderId())
				.productId(orderCreatedEvent.getProductId())
				.quantity(orderCreatedEvent.getQuantity())
				.userid(orderCreatedEvent.getUserId())
				.build();

		LOGGER.info("OrderCreatedEvent handled for Order "+ orderCreatedEvent.getOrderId() +
				" and Product "+ orderCreatedEvent.getProductId());
		
		commandGateway.send(reserveProductCommand, new CommandCallback<ReserveProductCommand, Object>() {

			@Override
			public void onResult(CommandMessage<? extends ReserveProductCommand> commandMessage, CommandResultMessage<?> commandResultMessage) {
				if(commandResultMessage.isExceptional()){
					// compensate transaction
				}
			}
		});
	}

	@SagaEventHandler(associationProperty="orderId")
	public void handle(ProductReservedEvent productReservedEvent) {
		// process payment
		LOGGER.info("ProductReservedEvent handled for Order "+ productReservedEvent.getOrderId() +
				" and Product "+ productReservedEvent.getProductId());
	}

}
