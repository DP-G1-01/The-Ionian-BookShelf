package org.springframework.samples.the_ionian_bookshelf.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.samples.the_ionian_bookshelf.model.Build;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;

public interface BuildRepository extends JpaRepository<Build, Integer> {

	@Query("select build from Build build where build.champion = ?1")
	Collection<Build> findByChampion(Champion champ);

}
