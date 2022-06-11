package com.ms.pedido.vallegrande.rest;

import com.ms.pedido.vallegrande.application.ProductService;
import com.ms.pedido.vallegrande.domain.Product;
import com.ms.pedido.vallegrande.domain.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    ProductRepository productRepository;
    @Autowired
    ProductService productService = new ProductService(productRepository);

    @GetMapping("/listProducts")
    public List<Product> findAll(){
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product findItemById(@PathVariable String id){
        return productService.findItemById(id);
    }

    @PostMapping
    public Product saveStudent(@RequestBody Product product){
        return productService.create(product);
    }

    @PutMapping
    public ResponseEntity<?>  update(@RequestBody Product product) {
        return productService.update(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        return productService.delete(id);
    }
}
