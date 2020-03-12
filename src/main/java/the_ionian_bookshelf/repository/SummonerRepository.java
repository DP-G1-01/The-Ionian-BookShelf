package the_ionian_bookshelf.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import the_ionian_bookshelf.model.Champion;
import the_ionian_bookshelf.model.Summoner;

public interface SummonerRepository extends JpaRepository<Summoner, Integer> {

	@Query("select summ from Summoner summ join summ.mains mains where ?1 in mains")
	Collection<Summoner> findByChampion(Champion champ);

	@Query("select summ from Summoner summ where summ.userAccount.id = ?1")
	Summoner findByUserAccountId(int id);

}
