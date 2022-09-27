package com.company.inventory.inventory.web.controller;

import com.company.inventory.inventory.response.CategoryReponseRest;
import com.company.inventory.inventory.services.CategoryServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CategoryRestController {
    private CategoryServiceImpl categoryService;

    /**
     * funcion que retorna las categorias.
     * @return return response entity con lista de categorias.
     */
    @GetMapping("/categories")
    public ResponseEntity<CategoryReponseRest> searchCategories() {
        return categoryService.search();
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryReponseRest> searchCategoriesByID(@PathVariable Long id) {
        return categoryService.searchById(id);
    }

}
