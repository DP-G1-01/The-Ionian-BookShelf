package the_ionian_bookshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import the_ionian_bookshelf.model.Reviewer;

public interface ReviewerRepository extends JpaRepository<Reviewer, Integer> {

	@Query("select rev from Reviewer rev where rev.userAccount.id = ?1")
	Reviewer findByUserAccountId(int id);

}
