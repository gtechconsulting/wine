package com.camilo.wine.service;

import com.camilo.wine.model.Client;
import reactor.core.publisher.Flux;

public interface ClientService {

    Flux<Client> getClientsShopping();
}
