package com.example.demoapis.controllers;

import com.example.demoapis.dto.ResponData;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoapis.models.entity.Products;
import com.example.demoapis.services.ProductService;

@RestController
@RequestMapping("api/products")
public class ProductController {


    //inject service nya
    @Autowired
    private ProductService productService;
    
    //save
    @PostMapping
    public ResponseEntity<ResponData<Products>> create(@Valid @RequestBody Products products, Errors errors){

        ResponData<Products> responseData = new ResponData<>();

        if (errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()){
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        responseData.setPayload(productService.save(products));
        return ResponseEntity.ok(responseData);
    }

    //get all
    @GetMapping
    public Iterable<Products> findAll(){
        return productService.findAll();
    }

    //get by id
    @GetMapping("/{id}")
    public Products findOne(@PathVariable("id") Long id){
        return productService.findOne(id);
    }

    //update
    @PutMapping
    public ResponseEntity<ResponData<Products>> update(@Valid @RequestBody Products products, Errors errors){
        ResponData<Products> responData = new ResponData<>();
        if (errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()){
                responData.getMessage().add(error.getDefaultMessage());
            }
            responData.setStatus(false);
            responData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responData);
        }
        responData.setStatus(true);
        responData.setPayload(productService.save(products));
        return ResponseEntity.ok(responData);
    }

    //delete by id
    @DeleteMapping("/{id}")
    public void removeOne(@PathVariable("id") Long id){
        productService.removeOne(id);
    }
}
