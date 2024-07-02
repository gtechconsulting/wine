package com.camilo.wine.dto;

import com.camilo.wine.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BiggestShoppingDTO {
    private String clientName;
    private String cpf;
    private Product product;
    private Long quantity;
    private Double total;
}
