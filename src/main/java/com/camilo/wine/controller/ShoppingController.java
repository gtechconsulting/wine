package com.camilo.wine.controller;

import com.camilo.wine.dto.BiggestShoppingDTO;
import com.camilo.wine.dto.ShoppingDTO;
import com.camilo.wine.exception.ProductNotFoundException;
import com.camilo.wine.exception.ShoppingNotFoundException;
import com.camilo.wine.model.Client;
import com.camilo.wine.model.Product;
import com.camilo.wine.model.Shopping;
import com.camilo.wine.service.ClientService;
import com.camilo.wine.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/shopping")
public class ShoppingController {


    private final ClientService clientService;
    private final ProductService productService;

    public ShoppingController(ClientService clientService, ProductService productService) {
        this.clientService = clientService;
        this.productService = productService;
    }

    @GetMapping(value = "/compras")
    public ResponseEntity<List<ShoppingDTO>> getShopping() {

        List<Client> clientsShop = clientService.getClientsShopping().collectList().flatMapMany(Flux::just).blockFirst();
        List<Product> products = productService.getProducts().collectList().flatMapMany(Flux::just).blockFirst();

        List<ShoppingDTO> shoppingDTOList = new ArrayList<>();
        assert clientsShop != null;

        clientsShop.forEach(client -> {
            double totalShop = 0.0;
            List<Product> shotProducts = new ArrayList<>();
            for(Shopping item : client.getCompras()) {
                assert products != null;
                Product product = products.stream().filter(p -> Objects.equals(p.getCodigo(), item.getCodigo())).findFirst().get();
                shotProducts.add(product);
                totalShop += item.getQuantidade() * product.getPreco();
            }

            var shoppingDTO = ShoppingDTO.builder().cpf(client.getCpf())
                    .name(client.getNome())
                    .total(totalShop)
                    .products(shotProducts)
                    .build();

            shoppingDTOList.add(shoppingDTO);
        });

        shoppingDTOList.sort(comparing(ShoppingDTO::getTotal).reversed());

        return new ResponseEntity<>(shoppingDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/maior-compra/{year}")
    public ResponseEntity<?> getBigestShoppingInYear(@PathVariable("year") Integer year) {

        List<Product> products = productService.getProducts().collectList().flatMapMany(Flux::just).blockFirst();
        if(products == null) {
            throw new ShoppingNotFoundException("No product shopping were found in the year: " + year);
        }
        List<Product> filteredProducts = products.stream().filter(p -> Objects.equals(p.getAnoCompra(), year)).toList();

        List<Client> clientsShop = clientService.getClientsShopping().collectList().flatMapMany(Flux::just).blockFirst();
        assert clientsShop != null;
        List<Client> filteredClients = new ArrayList<>();
        for(Product product : filteredProducts) {
            for(Client client : clientsShop) {
                List<Shopping> filtered = client.getCompras().stream().filter(i -> Objects.equals(i.getCodigo(), product.getCodigo())).toList();
                if(!filtered.isEmpty()) {
                    filteredClients.add(Client.builder()
                                    .nome(client.getNome())
                                    .cpf(client.getCpf())
                                    .compras(filtered)
                            .build());
                }
            }
        }

        List<ShoppingDTO> shoppingDTOList = new ArrayList<>();

        filteredClients.forEach(client -> {
            double totalShop = 0.0;
            long quantity = 0L;
            List<Product> shotProducts = new ArrayList<>();
            for(Shopping item : client.getCompras()) {
                Product product = products.stream().filter(p -> Objects.equals(p.getCodigo(), item.getCodigo())).findFirst().get();
                shotProducts.add(product);
                totalShop += item.getQuantidade() * product.getPreco();
                quantity = item.getQuantidade();
            }

            var shoppingDTO = ShoppingDTO.builder().cpf(client.getCpf())
                    .name(client.getNome())
                    .total(totalShop)
                    .quantidade(quantity)
                    .products(shotProducts)
                    .build();

            shoppingDTOList.add(shoppingDTO);
        });

        shoppingDTOList.sort(comparing(ShoppingDTO::getTotal).reversed());

        if(shoppingDTOList.isEmpty()) {
            return new ResponseEntity<>("No shopping were found in the year: " + year, HttpStatus.NOT_FOUND);
        }
        var biggestShop = shoppingDTOList.get(0);

        BiggestShoppingDTO biggestShoppingDTO = BiggestShoppingDTO.builder()
                .clientName(biggestShop.getName())
                .cpf(biggestShop.getCpf())
                .total(biggestShop.getTotal())
                .product(biggestShop.getProducts().get(0))
                .quantity(biggestShop.getQuantidade())
                .build();;

        return new ResponseEntity<>(biggestShoppingDTO, HttpStatus.OK);
    }
}
