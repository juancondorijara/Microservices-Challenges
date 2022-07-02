package com.financieraoh.springboot.app.item.clientes;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.financieraoh.springboot.app.item.models.Producto;


public interface ProductoClienteRest {
	
	@GetMapping("/listar")
	public List<Producto> listart();
	
	@GetMapping("/ver/{id}")
	public Producto detalle( @PathVariable Long id);

}
