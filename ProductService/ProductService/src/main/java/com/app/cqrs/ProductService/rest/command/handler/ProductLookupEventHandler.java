package com.app.cqrs.ProductService.rest.command.handler;

import com.app.cqrs.ProductService.entity.ProductLookupEntity;
import com.app.cqrs.ProductService.repository.ProductLookupRepository;
import com.app.cqrs.ProductService.rest.query.event.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductLookupEventHandler {

    @Autowired
    private ProductLookupRepository productLookupRepository;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductLookupEntity productLookupEntity = new ProductLookupEntity(event.getProductId(), event.getTitle());

        productLookupRepository.save(productLookupEntity);
    }
}
