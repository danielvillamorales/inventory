package com.company.inventory.inventory.response;

import com.company.inventory.inventory.web.dto.CategoryDto;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {
    private List<CategoryDto> categoryDto;
}
