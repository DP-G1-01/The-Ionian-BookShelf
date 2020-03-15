package the_ionian_bookshelf.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import the_ionian_bookshelf.model.RunePage;

public interface RunePageRepository extends JpaRepository<RunePage, Integer>{

	//@Query("select rune_page from rune_pages where rune_page.user_account_id = ?1")
	Collection<RunePage> findAllByUserAccountId(int id);
}
