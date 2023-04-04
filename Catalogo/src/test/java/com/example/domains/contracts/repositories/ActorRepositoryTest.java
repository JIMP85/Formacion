package com.example.domains.contracts.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.domains.entities.Actor;

@DataJpaTest
class ActorRepositoryTest {
		
		@Autowired
		private TestEntityManager em;
		
		@Autowired
		ActorRepository dao;
		
		@BeforeEach
		void setUp() throws Exception {
			var objeto = new Actor(0, "Ramon", "Garcia");
			objeto.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
			em.persist(objeto);
			objeto = new Actor(0, "Marianico", "ElCorto");
			objeto.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
			em.persist(objeto);
			objeto = new Actor(0, "Angel", "Martin");
			objeto.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
			em.persist(objeto);
			objeto = new Actor(0, "Dani", "Rovira");
			objeto.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
			em.persist(objeto);
			objeto = new Actor(0, "David", "Guapo");
			objeto.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
			em.persist(objeto);
		}

		@Test
		void testAll() {
			assertEquals(5, dao.findAll().size());
			assertEquals(3, dao.findAll().get(2).getActorId());
		}
		
		@Test
		void testOne() {
			var objeto = dao.findById(3);
			
			assertEquals("Martin", objeto.get().getLastName());
		}
		
		@Test
		void testSave() {
			var objeto = dao.save(new Actor(0, "Prueba", "PruebaApellido"));
			
			assertNotNull(objeto);
			assertEquals(6, objeto.getActorId());
		}
		
}


