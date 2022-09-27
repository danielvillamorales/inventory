package com.company.inventory.inventory.services;

import com.company.inventory.inventory.response.CategoryReponseRest;
import com.company.inventory.inventory.web.dto.CategoryDto;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity<CategoryReponseRest> search();

    ResponseEntity<CategoryReponseRest> searchById(Long id);

    ResponseEntity<CategoryReponseRest> save(CategoryDto categoryDto);

    ResponseEntity<CategoryReponseRest> update(Long id, CategoryDto categoryDto);

    ResponseEntity<CategoryReponseRest> delete(Long id);


}
