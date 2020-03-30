package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.the_ionian_bookshelf.model.Message;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.repository.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messRepo;

	@Autowired
	private ThreadService threadService;

	@Autowired
	private SummonerService summonerService;

	@Autowired
	public MessageService(MessageRepository messRepo) {
		this.messRepo = messRepo;
	}

	public Message create(int threadId) {
		assertNotEquals(threadId, 0);
		Message res = new Message();
		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
		res.setMoment(moment);
		Thread thread = this.threadService.findOne(threadId);
		res.setThread(thread);
		Summoner principal = this.summonerService.findByPrincipal();
		res.setSummoner(principal);
		assertNotNull(res);
		return res;
	}

	@Transactional
	public Iterable<Message> findAll() {
		Iterable<Message> res = this.messRepo.findAll();
		assertNotNull(res);
		return res;
	}

	@Transactional
	public Message findOneMesageById(int id) {
		assertTrue(id != 0);
		final Message res = this.messRepo.findById(id).get();
		assertNotNull(res);
		return res;
	}

	public Message saveMessage(Message message) throws DataAccessException {
		assertNotNull(message);
//		Actor principal = this.actorService.findByPrincipal();
//
//		assertTrue(this.actorService.checkAuthority(principal, Authority.ADMINISTRATOR)
//				|| this.actorService.checkAuthority(principal, Authority.REVIEWER)
//				|| this.actorService.checkAuthority(principal, Authority.SUMMONER));

		return this.messRepo.save(message);
	}

	public void delete(Message message) throws DataAccessException {
		assertNotNull(message);
//		Actor principal = this.actorService.findByPrincipal();
//
//		assertTrue(this.actorService.checkAuthority(principal, Authority.ADMINISTRATOR)
//				|| this.actorService.checkAuthority(principal, Authority.REVIEWER));
		this.messRepo.delete(message);
	}

	public Collection<Message> findByThread(Thread thread) {
		assertNotNull(thread);
		assertTrue(thread.getId() != 0);

		Collection<Message> res = this.messRepo.findByThread(thread);
		assertNotNull(res);

		return res;
	}

}
