package com.example.demoapis.models.repo;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demoapis.models.entity.Products;

public interface ProductRepo  extends CrudRepository<Products, Long>{

    //costoum function sercing by name
    List<Products> findByNameContains(String name);
    
}
