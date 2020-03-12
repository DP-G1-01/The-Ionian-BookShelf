package the_ionian_bookshelf.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import the_ionian_bookshelf.model.RunePage;

public interface RunePageRepository extends JpaRepository<RunePage, Integer>{

	@Query("select * from rune_pages where actor_id = ?1")
	Collection<RunePage> findAllByActorId(int id);
}
