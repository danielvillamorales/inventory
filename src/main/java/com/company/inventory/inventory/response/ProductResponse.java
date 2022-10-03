package com.company.inventory.inventory.response;

import com.company.inventory.inventory.web.dto.ProductDto;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {
    private List<ProductDto> products;
}
