package the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import the_ionian_bookshelf.model.Message;
import the_ionian_bookshelf.model.Thread;
import the_ionian_bookshelf.model.Vote;
import the_ionian_bookshelf.repository.ThreadRepository;

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
	public ThreadService(ThreadRepository threadRepository) {
		this.threadRepo=threadRepository;
	}
	
//	public Thread create() {
//		
//		Thread res = new Thread();
//		res.setTitle("New Thread");
//		res.setDescription("New description");
//		return res;
//	}
	
	@Transactional
	public Iterable<Thread> findAll() {

		Iterable<Thread> res = this.threadRepo.findAll();
		assertNotNull(res);

		return res;
	}

	@Transactional
	public Thread findOne(int id) {

		assertTrue(id != 0);

		final Thread res = this.threadRepo.findById(id).get();
		assertNotNull(res);

		return res;

	}

	public Thread save(Thread thread) throws DataAccessException{

		assertNotNull(thread);

//		Actor principal = this.actorService.findByPrincipal();
//
//		assertTrue(this.actorService.checkAuthority(principal, Authority.ADMINISTRATOR)
//				|| this.actorService.checkAuthority(principal, Authority.REVIEWER)
//				|| this.actorService.checkAuthority(principal, Authority.SUMMONER));

		return this.threadRepo.save(thread);
	}

	public void delete(Thread thread)  throws DataAccessException{

		assertNotNull(thread);
		//this.adminService.findByPrincipal();
//		assertTrue(this.actorService.checkAuthority(principal, Authority.ADMINISTRATOR)
//				|| this.actorService.checkAuthority(principal, Authority.REVIEWER));
		this.threadRepo.delete(thread);
	}

	public void deleteFromVote(Thread thread) throws DataAccessException {

		Collection<Vote> votes = this.voteService.findByThread(thread);
		for (Vote vote : votes) {
			this.voteService.delete(vote);
		}
	}

	public void deleteFromMessages(Thread thread) throws DataAccessException {

		Collection<Message> messages = this.messageService.findByThread(thread);
		for (Message message : messages) {
			this.messageService.delete(message);
		}
	}
}
