package com.company.inventory.inventory.web.controller;

import com.company.inventory.inventory.response.CategoryReponseRest;
import com.company.inventory.inventory.services.CategoryServiceImpl;
import com.company.inventory.inventory.web.dto.CategoryDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CategoryRestController {
    private CategoryServiceImpl categoryService;

    /**
     * funcion que retorna las categorias.
     *
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

    @PostMapping("/categories")
    public ResponseEntity<CategoryReponseRest> saveCategory(@RequestBody final CategoryDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryReponseRest> updateCategory(@PathVariable Long id,
            @RequestBody final CategoryDto categoryDto) {
        return categoryService.update(id,categoryDto);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<CategoryReponseRest> deleteCategoryById(@PathVariable Long id) {
        return categoryService.delete(id);
    }

}
