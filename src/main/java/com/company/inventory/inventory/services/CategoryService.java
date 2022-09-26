package com.company.inventory.inventory.services;

import com.company.inventory.inventory.response.CategoryReponseRest;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    public ResponseEntity<CategoryReponseRest> search();
}
