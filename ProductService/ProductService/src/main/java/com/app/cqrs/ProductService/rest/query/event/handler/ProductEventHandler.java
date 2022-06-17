package com.app.cqrs.ProductService.rest.query.event.handler;

import com.app.cqrs.CoreService.event.ProductReservedEvent;
import com.app.cqrs.ProductService.entity.ProductEntity;
import com.app.cqrs.ProductService.rest.query.event.ProductCreatedEvent;
import com.app.cqrs.ProductService.repository.ProductRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductEventHandler {

    @Autowired
    private ProductRepository productRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductEventHandler.class);

    @EventHandler
    public void on(ProductCreatedEvent event) throws Exception{
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(event,productEntity);

        try{
            productRepository.save(productEntity);
        }catch (IllegalArgumentException exception) {
            exception.printStackTrace();
        }
    }

    @EventHandler
    public void on(ProductReservedEvent event) {
        ProductEntity productEntity = productRepository.findByProductId(event.getProductId());
        productEntity.setQuantity(productEntity.getQuantity() - event.getQuantity());

        productRepository.save(productEntity);

        LOGGER.info("ProductReservedEvent handled for Order "+ event.getOrderId() +
                " and Product "+ event.getProductId());
    }

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handleIllegalArgumentException(IllegalArgumentException exception) {
        //Log message
    }

    @ExceptionHandler(resultType = Exception.class)
    public void handleGeneralException(Exception exception) throws Exception {
        throw exception;
    }
}
