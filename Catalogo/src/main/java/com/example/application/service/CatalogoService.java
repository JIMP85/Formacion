package com.example.application.service;

import java.sql.Timestamp;

import com.example.domains.entities.dtos.NovedadesDTO;

public interface CatalogoService {

	NovedadesDTO novedades(Timestamp fecha);
}
