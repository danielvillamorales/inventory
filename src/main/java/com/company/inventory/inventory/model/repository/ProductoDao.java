package com.company.inventory.inventory.model.repository;

import com.company.inventory.inventory.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ProductoDao extends JpaRepository<Product, Long> {
    /*@Query("select p from product p where p.name like %?1%")
    List<Product> findByNameLike(String name);*/
    List<Product> findByNameContainingIgnoreCase(String name);
}
