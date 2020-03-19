package the_ionian_bookshelf.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import the_ionian_bookshelf.model.RunePage;

public interface RunePageRepository extends JpaRepository<RunePage, Integer>{

	//Las querys utilizan todo como si estuvieses en java, no usa los nombres que aparecerían
	//en las tablas de la base de datos
	@Query("SELECT u FROM RunePage u WHERE u.summoner.id = ?1")
	Collection<RunePage> findAllByUserAccountId(int id);
}
