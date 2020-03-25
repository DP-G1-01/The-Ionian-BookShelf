package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Message;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.model.Vote;
import org.springframework.samples.the_ionian_bookshelf.repository.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
	
	@Autowired
	private MessageRepository messRepo;

	public Collection<Message> findByThread(Thread thread) {
		assertNotNull(thread);
		assertTrue(thread.getId() != 0);
		
		Collection<Message> res = this.messRepo.findByThread(thread);
		assertNotNull(res);
		
		return res;
	}

	public void delete(Message message) {
		this.messRepo.delete(message);
	}

}
