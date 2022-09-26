package com.company.inventory.inventory.model.repository;

import com.company.inventory.inventory.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category,Long> {
}
