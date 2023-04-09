package com.example.application.service;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.contracts.services.CategoryService;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.FilmDTO;
import com.example.domains.entities.dtos.NovedadesDTO;

import jakarta.transaction.Transactional;

public class CatalogoServiceImpl implements CatalogoService {

	@Autowired
	private FilmService filmSrv;
	@Autowired
	private ActorService artorSrv;
	@Autowired
	private CategoryService categorySrv;
	@Autowired
	private LanguageService languageSrv;
	
	@Autowired
	@Transactional
	public NovedadesDTO novedades(Timestamp fecha) {
		if(fecha == null)
			fecha = Timestamp.from(Instant.now().minusSeconds(40000));
		return new NovedadesDTO(
				filmSrv.novedades(fecha).stream().map(item -> FilmDTO.from(item)).toList(), 
				artorSrv.novedades(fecha).stream().map(item -> ActorDTO.from(item)).toList(), 
				categorySrv.novedades(fecha), 
				languageSrv.novedades(fecha)
				);
	}
}
