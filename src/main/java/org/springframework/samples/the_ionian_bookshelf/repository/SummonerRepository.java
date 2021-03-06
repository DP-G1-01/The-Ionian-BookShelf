package org.springframework.samples.the_ionian_bookshelf.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;

public interface SummonerRepository extends JpaRepository<Summoner, Integer> {

	@Query("select summ from Summoner summ join summ.mains mains where ?1 in mains")
	Collection<Summoner> findByChampion(Champion champ);

	@Query("select summ from Summoner summ where summ.user.username = ?1")
	Summoner findByUsername(String username);

}