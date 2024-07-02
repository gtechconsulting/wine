package com.camilo.wine.controller;

import com.camilo.wine.dto.ShoppingDTO;
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

    @GetMapping(value = "/maior-compra/ano")
    public ResponseEntity<String> getRecomendation() {
        return new ResponseEntity<>("Recomendacao de vinho", HttpStatus.OK);
    }
}
