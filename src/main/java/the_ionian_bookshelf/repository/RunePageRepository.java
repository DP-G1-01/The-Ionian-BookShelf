package the_ionian_bookshelf.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import the_ionian_bookshelf.model.RunePage;

public interface RunePageRepository extends JpaRepository<RunePage, Integer>{

//	@Query("SELECT * FROM RUNE_PAGES WHERE summoner_id = ?1")
//	Collection<RunePage> findAllByUserAccountId(int id);
}
