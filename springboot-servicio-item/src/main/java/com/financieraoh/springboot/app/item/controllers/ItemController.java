package com.financieraoh.springboot.app.item.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.financieraoh.springboot.app.item.models.Item;
import com.financieraoh.springboot.app.item.models.Producto;
import com.financieraoh.springboot.app.item.models.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class ItemController {

	@Autowired
	@Qualifier("serviceRestTemplate")
	private ItemService itemService;
	
	@GetMapping("/listar")
	public List<Item> listar(){
		return itemService.findall();
	}
	
	@HystrixCommand(fallbackMethod="metodoAlternativo")
	@GetMapping("/ver/{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad){
		return itemService.findBy(id, cantidad);
	}
	
	public Item metodoAlternativo(Long id, Integer cantidad){
		Item item = new Item();
		Producto producto = new Producto();
		
		item.setCantidad(cantidad);
		producto.setId(id);
		producto.setNombre("hystrix");
		producto.setPrecio(1000.00);
		item.setProducto(producto);
		return item;
	}
	
}
