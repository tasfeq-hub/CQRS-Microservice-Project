package com.app.cqrs.ProductService.rest.query;

import com.app.cqrs.ProductService.entity.ProductEntity;
import com.app.cqrs.ProductService.repository.ProductRepository;
import com.app.cqrs.ProductService.rest.query.model.ProductRestModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductQueryHandler {

    @Autowired
    private ProductRepository productRepository;

    @QueryHandler
    public List<ProductRestModel> findProducts(FindProductsQuery query){
        List<ProductRestModel> products = new ArrayList<>();

        List<ProductEntity> allProducts = productRepository.findAll();

        for(ProductEntity productEntity: allProducts){
            ProductRestModel productRestModel = new ProductRestModel();
            BeanUtils.copyProperties(productEntity,productRestModel);
            products.add(productRestModel);
        }

        return products;
    }
}
