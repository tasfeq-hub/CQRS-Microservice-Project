package com.app.cqrs.ProductService.event.handler;

import com.app.cqrs.ProductService.entity.ProductEntity;
import com.app.cqrs.ProductService.event.ProductCreatedEvent;
import com.app.cqrs.ProductService.repository.ProductRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductEventHandler {

    @Autowired
    private ProductRepository productRepository;

    @EventHandler
    public void on(ProductCreatedEvent event){
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(event,productEntity);

        productRepository.save(productEntity);
    }
}
