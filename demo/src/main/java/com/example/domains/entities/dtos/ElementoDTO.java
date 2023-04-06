package com.example.domains.entities.dtos;

import lombok.Value;

@Value //Porque será solo salida
public class ElementoDTO <K, V> {
	private K key;
	private V value;
}
