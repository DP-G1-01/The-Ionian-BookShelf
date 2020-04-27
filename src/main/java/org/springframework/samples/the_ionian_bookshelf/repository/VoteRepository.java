package org.springframework.samples.the_ionian_bookshelf.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.samples.the_ionian_bookshelf.model.Vote;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {

	@Query("select vote from Vote vote where vote.thread.id = ?1")
	Collection<Vote> findByThread(int id);
	
	@Query("select count(vote) from Vote vote where vote.thread.id = ?1 and vote.status = TRUE")
	Integer countPositivesVotesByThread(int id);

	@Query("select count(vote) from Vote vote where vote.thread.id = ?1 and vote.status = FALSE")
	Integer countNegativesVotesByThread(int id);
	
	@Query("select vote from Vote vote where vote.message.id = ?1")
	Collection<Vote> findByMessageId(int id);
	
	@Query("select count(vote) from Vote vote where vote.message.id = ?1 and vote.status = TRUE")
	Integer countPositivesVotesByMessage(int id);

	@Query("select count(vote) from Vote vote where vote.message.id = ?1 and vote.status = FALSE")
	Integer countNegativesVotesByMessage(int id);

}
