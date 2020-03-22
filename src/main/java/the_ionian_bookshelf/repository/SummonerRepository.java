package the_ionian_bookshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import the_ionian_bookshelf.model.Summoner;

@Repository
public interface SummonerRepository extends JpaRepository<Summoner, Integer>{

	}
