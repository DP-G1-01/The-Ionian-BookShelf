package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Actor;
import org.springframework.samples.the_ionian_bookshelf.model.Message;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.model.Vote;
import org.springframework.samples.the_ionian_bookshelf.repository.ThreadRepository;
import org.springframework.stereotype.Service;

@Service
public class ThreadService {

	@Autowired
	private VoteService voteService;
	@Autowired
	private ThreadRepository threadRepo;
	@Autowired
	private ActorService actorService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private AuthoritiesService authService;

	public Thread create() {

		Thread res = new Thread();
		res.setTitle("New Thread");
		res.setDescription("New description");
		return res;
	}

	public Collection<Thread> findAll() {

		Collection<Thread> res = this.threadRepo.findAll();
		assertNotNull(res);

		return res;
	}

	public Thread findOne(int id) {

		assertTrue(id != 0);

		final Thread res = this.threadRepo.findById(id).get();
		assertNotNull(res);

		return res;

	}

	public Thread save(Thread thread) {

		assertNotNull(thread);

		assertTrue(this.authService.checkAuthorities("administrator") || this.authService.checkAuthorities("summoner")
				|| this.authService.checkAuthorities("reviewer"));

		return this.threadRepo.save(thread);
	}

	public void delete(Thread thread) {

		assertNotNull(thread);
		// this.adminService.findByPrincipal();
		this.threadRepo.delete(thread);
	}

	public void deleteFromVote(Thread thread) {

		Collection<Vote> votes = this.voteService.findByThread(thread);
		for (Vote vote : votes) {
			this.voteService.delete(vote);
		}
	}

	public void deleteFromMessages(Thread thread) {

		Collection<Message> messages = this.messageService.findByThread(thread);
		for (Message message : messages) {
			this.messageService.delete(message);
		}
	}
}
