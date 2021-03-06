package org.springframework.samples.the_ionian_bookshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.samples.the_ionian_bookshelf.model.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select admin from Administrator admin where admin.user.username = ?1")
	Administrator findByUsername(String username);

}
