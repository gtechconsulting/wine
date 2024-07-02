package com.camilo.wine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private String codigo;
    private String tipoVinho;
    private Double preco;
    private String safra;
    private Integer anoCompra;
}
