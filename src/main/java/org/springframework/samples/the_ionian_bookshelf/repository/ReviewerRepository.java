package org.springframework.samples.the_ionian_bookshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.samples.the_ionian_bookshelf.model.Reviewer;

public interface ReviewerRepository extends JpaRepository<Reviewer, Integer> {

	@Query("select rev from Reviewer rev where rev.user.username = ?1")
	Reviewer findByUsername(String username);

}
