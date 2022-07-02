package com.financieraoh.springboot.app.item.models.service;

import java.util.List;

import com.financieraoh.springboot.app.item.models.Item;

public interface ItemService {
	public List<Item> findall();
	public Item findBy(Long id, Integer cantidad);

}
