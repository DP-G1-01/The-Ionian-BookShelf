package the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import the_ionian_bookshelf.model.Thread;
import the_ionian_bookshelf.model.Vote;
import the_ionian_bookshelf.repository.VoteRepository;

@Service
public class VoteService {
	
	@Autowired
	private VoteRepository voteRepo;

	public Collection<Vote> findByThread(Thread thread) {
		
		assertNotNull(thread);
		assertTrue(thread.getId() != 0);
		
		Collection<Vote> res = this.voteRepo.findByThread(thread);
		assertNotNull(res);
		
		return res;
	}

	public void delete(Vote vote) {
		this.voteRepo.delete(vote);
		
	}

}
