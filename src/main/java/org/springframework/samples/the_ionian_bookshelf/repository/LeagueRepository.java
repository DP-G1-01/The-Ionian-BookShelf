package org.springframework.samples.the_ionian_bookshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.samples.the_ionian_bookshelf.model.League;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;

public interface LeagueRepository extends JpaRepository<League, Integer> {

	@Query("select league from League league where league.name = 'Basic'")
	League findBasicLeague();

	@Query("select league from League league where league.thread = ?1")
	League findByThread(Thread thread);

}