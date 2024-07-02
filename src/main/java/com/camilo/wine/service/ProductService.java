package com.camilo.wine.service;

import com.camilo.wine.model.Product;
import reactor.core.publisher.Flux;

public interface ProductService {

    Flux<Product> getProducts();
}
