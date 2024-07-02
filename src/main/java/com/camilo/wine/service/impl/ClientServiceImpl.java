package com.camilo.wine.service.impl;

import com.camilo.wine.model.Client;
import com.camilo.wine.service.ClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class ClientServiceImpl implements ClientService {
    private final WebClient webClient;

    @Value("${uri.client.buy.api}")
    private String clientUri;

    private static final Logger logger = LogManager.getLogger(ClientServiceImpl.class);

    public ClientServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<Client> getClientsShopping() {
        return webClient.get()
                .uri(clientUri)
                .retrieve()
                .bodyToFlux(Client.class)
                .doOnError(throwable -> logger.error("Failed to call api", throwable));

    }
}
