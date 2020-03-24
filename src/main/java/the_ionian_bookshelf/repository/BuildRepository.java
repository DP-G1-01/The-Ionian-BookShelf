package the_ionian_bookshelf.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import the_ionian_bookshelf.model.Build;

public interface BuildRepository extends JpaRepository<Build, Integer> {

	Build findBuildById(int id);

	void removeBuildById(int id);

	@Query("SELECT b FROM Build b WHERE b.runePage.id = ?1")
	Collection<Build> findAllByRunePage(Integer id);
}
