package com.camilo.wine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private String nome;
    private String cpf;
    private List<Shopping> compras;
}
