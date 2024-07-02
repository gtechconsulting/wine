package com.camilo.wine.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("tipo_vinho")
    private String tipoVinho;
    private Double preco;
    private String safra;
    @JsonProperty("ano_compra")
    private Integer anoCompra;
}
