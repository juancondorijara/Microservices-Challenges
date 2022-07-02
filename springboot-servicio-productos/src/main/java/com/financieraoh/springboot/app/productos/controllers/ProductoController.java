package com.financieraoh.springboot.app.productos.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.financieraoh.springboot.app.productos.models.entity.Producto;
import com.financieraoh.springboot.app.productos.models.service.IProductoService;




@RestController
public class ProductoController {
	
	Logger logger = LoggerFactory.getLogger(ProductoController.class);

	
	
	@Autowired
	private IProductoService productoService;
	
	@GetMapping("/listar")
	public List<Producto> listar(){
		logger.info("Listando productos");
		return productoService.findAll().stream().map(producto -> {
			return producto;
		}).collect(Collectors.toList());
		
	}
	
	@GetMapping("/ver/{id}")
	public Producto detalle( @PathVariable Long id) {
		logger.info("Buscando productos");
		return productoService.finById(id);
	}
}
