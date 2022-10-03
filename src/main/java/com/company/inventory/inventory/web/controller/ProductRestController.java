package com.company.inventory.inventory.web.controller;

import com.company.inventory.inventory.config.Util;
import com.company.inventory.inventory.response.ProductResponseRest;
import com.company.inventory.inventory.services.ProductServiceImpl;
import com.company.inventory.inventory.web.dto.ProductDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = {"http://localhost:4200"})
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductRestController {
    private ProductServiceImpl productService;

    @PostMapping("/products")
    public ResponseEntity<ProductResponseRest> save(
            @RequestParam("picture") MultipartFile picture,
            @RequestParam("name") String name,
            @RequestParam("price") Long price,
            @RequestParam("account") int account,
            @RequestParam("categoryiId") Long categoryId
    ) throws IOException {
        ProductDto productoDto = ProductDto.builder()
                .name(name)
                .account(account)
                .price(price)
                .picture(Util.compressZLib((picture.getBytes())))
                .build();

        return productService.save(productoDto, categoryId);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> findOne(@PathVariable Long id) {
        return productService.findOne(id);
    }

    @GetMapping("/products/filter/{name}")
    public ResponseEntity<ProductResponseRest> findLikeName(@PathVariable String name) {
        return productService.findName(name);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> deleteOne(@PathVariable Long id) {
        return productService.delete(id);
    }

    @GetMapping("/products")
    public ResponseEntity<ProductResponseRest> findAll() {
        return productService.findAll();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> save(
            @RequestParam("picture") MultipartFile picture,
            @RequestParam("name") String name,
            @RequestParam("price") Long price,
            @RequestParam("account") int account,
            @RequestParam("categoryiId") Long categoryId,
            @PathVariable Long id
    ) throws IOException {
        ProductDto productoDto = ProductDto.builder()
                .name(name)
                .account(account)
                .price(price)
                .picture(Util.compressZLib((picture.getBytes())))
                .build();

        return productService.updateOne(productoDto, id, categoryId);
    }

}
