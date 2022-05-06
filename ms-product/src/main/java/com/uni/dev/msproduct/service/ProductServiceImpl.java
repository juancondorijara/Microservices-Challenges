package com.uni.dev.msproduct.service;

import com.uni.dev.msproduct.model.Product;
import com.uni.dev.msproduct.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        if (!optionalProduct.isPresent()) throw new RuntimeException("No se encontro el producto a actualizar");
        return productRepository.save(product);
    }

    @Override
    public void delete(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) throw new RuntimeException("No se encontro el producto a eliminar");
        productRepository.deleteById(id);
    }

}
