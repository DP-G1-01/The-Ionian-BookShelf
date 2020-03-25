package org.springframework.samples.the_ionian_bookshelf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.samples.the_ionian_bookshelf.model.Build;

public interface BuildRepository extends JpaRepository<Build, Integer> {
	
	Build findBuildById(int id);

	void removeBuildById(int id);

	@Query("select distinct build from Build build " + 
			"join build.items item where item.id = ?1")
	List<Build> findBuildsByItemId(int id);
}

