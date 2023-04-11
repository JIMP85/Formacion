package com.example.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.domains.core.repositories.contracts.RepositoryWithProjections;
import com.example.domains.entities.Category;
import com.example.domains.entities.Language;

import jakarta.validation.constraints.NotNull;

@RepositoryRestResource(path="idiomas", itemResourceRel = "idioma", collectionResourceRel = "idiomas")
public interface LanguageRepository extends JpaRepository<Language, Integer>, JpaSpecificationExecutor<Language>, RepositoryWithProjections{

	@RestResource(path ="novedades")
	List<Language> findByLastUpdateGreaterThanEqualOrderByLastUpdate(@NotNull Timestamp fecha);
	
	<T> List <T> findAllBy(Class<T> type);

	@RestResource(exported = false)
	@Override
	void deleteById(Integer id);
}
