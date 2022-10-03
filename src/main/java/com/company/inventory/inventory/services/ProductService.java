package com.company.inventory.inventory.services;

import com.company.inventory.inventory.response.ProductResponseRest;
import com.company.inventory.inventory.web.dto.ProductDto;
import org.springframework.http.ResponseEntity;

public interface ProductService {

    public ResponseEntity<ProductResponseRest> save(ProductDto product, Long categoryId);

    public ResponseEntity<ProductResponseRest> findOne(Long id);

    public ResponseEntity<ProductResponseRest> findName(String name);

    public ResponseEntity<ProductResponseRest> delete(Long id);

    public ResponseEntity<ProductResponseRest> findAll();

    public ResponseEntity<ProductResponseRest> updateOne(ProductDto productoDto , Long id, Long categoryId);

}
