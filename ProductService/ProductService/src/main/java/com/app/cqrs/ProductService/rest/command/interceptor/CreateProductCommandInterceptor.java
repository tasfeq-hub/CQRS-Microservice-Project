package com.app.cqrs.ProductService.rest.command.interceptor;

import com.app.cqrs.ProductService.entity.ProductLookupEntity;
import com.app.cqrs.ProductService.repository.ProductLookupRepository;
import com.app.cqrs.ProductService.rest.command.CreateProductCommand;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    @Autowired
    private ProductLookupRepository productLookupRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateProductCommandInterceptor.class);

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> list) {
        return (index, command) -> {
            LOGGER.info("Intercepted command" + command.getPayloadType());

            if(CreateProductCommand.class.equals(command.getPayloadType())) {
                CreateProductCommand createProductCommand = (CreateProductCommand) command.getPayload();

                ProductLookupEntity productLookupEntity =
                        productLookupRepository.findByProductIdOrTitle(createProductCommand.getProductId(), createProductCommand.getTitle());

                if(productLookupEntity != null) {
                    throw new IllegalStateException("Product Already Exists!");
                }

                /*
                if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0){
                    throw new IllegalArgumentException("price can not be less than or equal to zero!");
                }
                */
            }
            return command;
        };
    }
}
