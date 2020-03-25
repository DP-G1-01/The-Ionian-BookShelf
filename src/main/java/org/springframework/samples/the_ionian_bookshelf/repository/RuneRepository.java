package org.springframework.samples.the_ionian_bookshelf.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.the_ionian_bookshelf.model.Rune;

public interface RuneRepository extends JpaRepository<Rune, Integer> {
	
	Collection<Rune> findByName(String name) throws DataAccessException;

}
