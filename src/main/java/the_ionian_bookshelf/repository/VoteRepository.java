package the_ionian_bookshelf.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import the_ionian_bookshelf.model.Thread;
import the_ionian_bookshelf.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer>{

	@Query("select vote from Vote vote where vote.thread = ?1")
	Collection<Vote> findByThread(Thread thread);

}
