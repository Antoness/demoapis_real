package com.example.demoapis.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demoapis.models.entity.Products;
import com.example.demoapis.models.repo.ProductRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {
    
    //inject ke repositorynya
    @Autowired
    private ProductRepo productRepo;

    //crerate
    public Products save(Products product){
        return productRepo.save(product);
    }

    // cari berdasrkan id
    public Products findOne(Long id){
        Optional<Products> products = productRepo.findById(id);
        if (!products.isPresent()) {
            return null;
        }
        return products.get();
    }

    // cari all
    public Iterable<Products> findAll(){
        return productRepo.findAll();
    }

    // delete
    public void removeOne(Long id){
         productRepo.deleteById(id);
    }


    //memanggil function dari repo
    public List<Products> findByName(String name){
        return productRepo.findByNameContains(name);
    }
}
