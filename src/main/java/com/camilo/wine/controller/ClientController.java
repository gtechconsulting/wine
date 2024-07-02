package com.camilo.wine.controller;

import com.camilo.wine.dto.LoyalCustomerDTO;
import com.camilo.wine.dto.RecommendationDTO;
import com.camilo.wine.model.Client;
import com.camilo.wine.model.Product;
import com.camilo.wine.model.Shopping;
import com.camilo.wine.service.ClientService;
import com.camilo.wine.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Comparator.comparing;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;
    private final ProductService productService;

    public ClientController(ClientService clientService, ProductService productService) {
        this.clientService = clientService;
        this.productService = productService;
    }

    @GetMapping(value = "/clientes-fieis")
    public ResponseEntity<List<LoyalCustomerDTO>> getShopping() {

        List<Client> clientsShop = clientService.getClientsShopping().collectList().flatMapMany(Flux::just).blockFirst();

        List<LoyalCustomerDTO> customers = new ArrayList<>();

        assert clientsShop != null;
        clientsShop.forEach(client -> {
            customers.add(LoyalCustomerDTO.builder()
                            .clientName(client.getNome())
                            .cpf(client.getCpf())
                            .totalShopped(client.getCompras().size())
                    .build());
        });

        customers.sort(comparing(LoyalCustomerDTO::getTotalShopped).reversed());

        return new ResponseEntity<>(customers.subList(0,3), HttpStatus.OK);
    }

    @GetMapping(value = "/recomendacao/cliente/tipo")
    public ResponseEntity<List<RecommendationDTO>> getRecomendation() {
        List<Client> clientsShop = clientService.getClientsShopping().collectList().flatMapMany(Flux::just).blockFirst();
        List<Product> products = productService.getProducts().collectList().flatMapMany(Flux::just).blockFirst();
        List<RecommendationDTO> recomendationDTOS = new ArrayList<>();
        assert clientsShop != null;
        for(Client client : clientsShop) {
            client.getCompras().sort(comparing(Shopping::getQuantidade).reversed());
            Shopping principal = client.getCompras().get(0);
            assert products != null;
            Product product = products.stream().filter(p -> Objects.equals(p.getCodigo(), principal.getCodigo())).findFirst().get();
            RecommendationDTO recommendationDTO = RecommendationDTO.builder()
                    .name(client.getNome())
                    .recommended(product.getTipoVinho())
                    .build();

            recomendationDTOS.add(recommendationDTO);
        }
        return new ResponseEntity<>(recomendationDTOS, HttpStatus.OK);
    }
}
