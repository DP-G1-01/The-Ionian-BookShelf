package org.springframework.samples.the_ionian_bookshelf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.samples.the_ionian_bookshelf.model.League;

public interface LeagueRepository extends JpaRepository<League, Integer> {

	@Query("select league from League league where league.name = 'Basic'")
	League findBasicLeague();

}
