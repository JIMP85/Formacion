package com.example.ioc;

import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Repository
@Primary
public class StringRepositoryImpl implements StringRepository {

	private String ultimo = "";
	
	@Override
	public String load() {
		// TODO Auto-generated method stub
		return "Soy el StingRepositoryImpl";
	}

	@Override
	public void save(String item) {
		System.out.println("Guardo el item: " +item);

	}

}
