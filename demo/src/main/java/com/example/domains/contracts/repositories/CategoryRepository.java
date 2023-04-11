package com.example.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.domains.core.repositories.contracts.RepositoryWithProjections;
import com.example.domains.entities.Category;

import jakarta.validation.constraints.NotNull;

public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category>, RepositoryWithProjections {

	<T> List <T> findAllBy(Class<T> type);

	List<Category> findByLastUpdateGreaterThanEqualOrderByLastUpdate(@NotNull Timestamp fecha);
}
