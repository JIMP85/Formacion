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
			var item = new Actor(0, "Ramon", "Garcia");
			item.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
			em.persist(item);
			item = new Actor(0, "Marianico", "Elcorto");
			item.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
			em.persist(item);
			item = new Actor(0, "Angel", "Martin");
			item.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
			em.persist(item);
			item = new Actor(0, "Dani", "Rovira");
			item.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
			em.persist(item);
			item = new Actor(0, "David", "Guapo");
			item.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
			em.persist(item);
		}

		@Test
		void testAll() {
			assertEquals(5, dao.findAll().size());
			assertEquals(3, dao.findAll().get(2).getActorId());
		}
		
		@Test
		void testOne() {
			var item = dao.findById(1);
			
			assertTrue(item.isPresent());
			assertEquals("Pepito", item.get().getFirstName());
		}
		@Test
		void testSave() {
			var item = dao.save(new Actor(0, "Prueba", "Prueba_apellido"));
			
			assertNotNull(item);
			assertEquals(4, item.getActorId());
		}
		
}


