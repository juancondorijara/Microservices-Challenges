package com.uni.dev.msproduct.controller;

import com.uni.dev.msproduct.model.Product;
import com.uni.dev.msproduct.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/ms-product/v1/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public List<Product> findAll(){
        return productService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Product create(@RequestBody Product product){
        return productService.create(product);
    }

    @PutMapping
    public Product update(@RequestBody Product product) {
        return productService.update(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        productService.delete(id);
    }
}
