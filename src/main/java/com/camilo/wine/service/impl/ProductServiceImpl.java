package com.camilo.wine.service.impl;

import com.camilo.wine.model.Product;
import com.camilo.wine.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class ProductServiceImpl implements ProductService {
    private final WebClient webClient;

    @Value("${uri.product.api}")
    private String productUri;

    private static final Logger logger = LogManager.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Flux<Product> getProducts() {
        return webClient.get()
                .uri(productUri)
                .retrieve()
                .bodyToFlux(Product.class)
                .doOnError(throwable -> logger.error("Failed to call api", throwable));
    }
}
