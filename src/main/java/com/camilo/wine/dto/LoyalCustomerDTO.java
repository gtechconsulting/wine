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
public class LoyalCustomerDTO {
    private String clientName;
    private String cpf;
    private Integer totalShopped;
}
