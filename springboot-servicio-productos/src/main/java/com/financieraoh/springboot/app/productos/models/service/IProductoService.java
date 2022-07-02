package com.financieraoh.springboot.app.productos.models.service;

import java.util.List;

import com.financieraoh.springboot.app.productos.models.entity.Producto;

public interface IProductoService {
	
	public List<Producto> findAll();
	public Producto finById(Long id);
	

}
