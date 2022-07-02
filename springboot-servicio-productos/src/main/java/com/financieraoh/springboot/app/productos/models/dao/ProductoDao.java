package com.financieraoh.springboot.app.productos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.financieraoh.springboot.app.productos.models.entity.Producto;

public interface ProductoDao extends CrudRepository<Producto, Long>{

}
