package com.example;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.repositories.LanguageRepository;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.ActorDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class CatalogoApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}
	
//	@Autowired
//	FilmService srv;
	
	@Override
	@Transactional
	public void run(String... args) throws Exception {
		//Es aconsejable poner esto para saber en que linea de consola ha dejado de arrancar Spring
		System.out.println("---------Aplicación arrancada-------------"); 
		
//		var pelicula = new Film("Pelicula de prueba", new Language(4), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0));
//		pelicula.setRating(Rating.PARENTAL_GUIDANCE_SUGGESTED);
//		pelicula.addActor(1);
//		pelicula.addActor(5);
//		pelicula.addActor(9);
//		pelicula.addCategory(6);
//		pelicula.addCategory(3);
//		System.out.println(pelicula.getErrorsMessage());
//		pelicula = srv.añadir(pelicula);
//		System.out.println(pelicula.getFilmId());
//		pelicula = srv.getOne(1013).get();
//		pelicula.removeActor(new Actor(1));
//		pelicula.removeActor(new Actor(2));
//		pelicula.addActor(4);
//		pelicula.removeCategory(pelicula.getCategories().get(0));
//		pelicula.addCategory(1);
//		pelicula.setTitle("Cambio titulo pelicula");
//		srv.modificar(pelicula);
//		srv.deleteById(1001);
	}
}
