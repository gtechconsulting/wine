package com.camilo.wine.dto;

import com.camilo.wine.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingDTO {
    private String name;
    private String cpf;
    private Double total;
    private List<Product> products;
}
