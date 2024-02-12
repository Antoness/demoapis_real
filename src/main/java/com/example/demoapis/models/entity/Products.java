package com.example.demoapis.models.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "tbl_products") // saat di run jpa akan mengecek apakah di db ada table tbl_products jika belum dia akan membuat table, dan akan menghubungkan ke class product
public class Products  implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement ID
    private Long id;

    @NotEmpty(message = "Name is Required")
    @Column(name = "product_name", length = 100)
    private String name;

    @NotEmpty(message = "Description is Required")
    @Column(name = "product_desription", length = 500)
    private String description;

    private String price;

    // constraction kosong
    public Products() {
    }

    // constraction
    public Products(Long id, String name, String description, String price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }


    // getter setter
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getPrice() {
        return price;
    }


    public void setPrice(String price) {
        this.price = price;
    }


    
    
}
