package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Vote;
import org.springframework.samples.the_ionian_bookshelf.model.Message;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.repository.VoteRepository;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

	@Autowired
	private VoteRepository voteRepo;

	public Collection<Vote> findByThread(Thread thread) {

		assertNotNull(thread);
		assertTrue(thread.getId() != 0);

		Collection<Vote> res = this.voteRepo.findByThread(thread.getId());
		assertNotNull(res);

		return res;
	}
	
	public void deleteByThreadId(int id) {
		Collection<Vote> votes = this.voteRepo.findByThread(id);
		for (Vote vote : votes) {
			this.delete(vote);
		}
	}
	
	public Integer getPuntuationThread(Thread thread) {
		assertNotNull(thread);
		assertTrue(thread.getId() != 0);
		Integer positives = this.voteRepo.countPositivesVotesByThread(thread.getId());
		Integer negatives = this.voteRepo.countNegativesVotesByThread(thread.getId());
		
		return positives-negatives;
		
	}

	public Collection<Vote> findByMessageId(int id) {
		assertTrue(id != 0);
		Collection<Vote> res = this.voteRepo.findByMessageId(id);
		assertNotNull(res);
		return res;
	}

	public void deleteByMessageId(int id) {
		Collection<Vote> votes = this.voteRepo.findByMessageId(id);
		for (Vote vote : votes) {
			this.delete(vote);
		}
	}
	
	public Integer getPuntuationMessage(Message message) {
		assertNotNull(message);
		assertTrue(message.getId() != 0);
		Integer positives = this.voteRepo.countPositivesVotesByThread(message.getId());
		Integer negatives = this.voteRepo.countNegativesVotesByThread(message.getId());
		
		return positives-negatives;
		
	}

	public void delete(Vote vote) {
		this.voteRepo.delete(vote);
	}

}
