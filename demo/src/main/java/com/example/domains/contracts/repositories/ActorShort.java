package com.example.domains.contracts.repositories;

import org.springframework.beans.factory.annotation.Value;

public interface ActorShort {

	int getActorId();
	
	@Value("#{target.firstname + ' ' + target.lastName}")
	String getNombre();
}
