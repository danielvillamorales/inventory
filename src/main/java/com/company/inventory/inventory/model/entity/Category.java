package com.company.inventory.inventory.model.entity;

import lombok.Data;


import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = "category")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
}
