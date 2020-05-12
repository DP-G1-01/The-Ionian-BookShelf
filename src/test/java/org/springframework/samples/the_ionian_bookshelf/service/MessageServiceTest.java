package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.Assert.assertTrue;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.the_ionian_bookshelf.model.Message;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.utilities.AbstractTest;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class MessageServiceTest extends AbstractTest {

	@Autowired
	private MessageService messageService;

	@Autowired
	private ThreadService threadService;

	@Test
	void findAllTest() {

		Iterable<Message> aux = this.messageService.findAll();
		Collection<Message> res = new ArrayList<Message>();
		for (Message message : aux) {
			res.add(message);
		}
		assertTrue(!res.isEmpty());
	}

	@Test
	void findAllCollectionTest() {

		Collection<Message> res = this.messageService.findAllCollection();
		assertTrue(!res.isEmpty());
	}

	@DisplayName("FindOne Test")
	@ParameterizedTest(name = "\"{0}\": Message´s id has to exist")
	@CsvSource({ "0", "50", "100" })
	void findOneTest(Integer messageId) {

		if (messageId != 0 && this.messageService.exists(messageId) == false) {
			assertThrows(NoSuchElementException.class, () -> {
				this.messageService.findOneMesageById(messageId);
			});
		} else if (messageId == 0) {
			assertThrows(AssertionError.class, () -> {
				this.messageService.findOneMesageById(messageId);
			});
		} else {
			this.messageService.findOneMesageById(messageId);
		}
	}

	@DisplayName("Negative Save Message Test")
	@ParameterizedTest(name = "\"{0}\" Represents message´s id -> Messages are not valid to save")
	@CsvSource({ "null, 100", "100, 100" })
	void negativeSaveTest(String messageId, int threadId) {

		this.authenticate("RAIMUNDOKARATE98");

		if (messageId.equals("null")) {
			Integer auxMessId = null;
			Message toSave = this.messageService.create(threadId);
			toSave.setId(auxMessId);
			assertThrows(ConstraintViolationException.class, () -> {
				this.messageService.saveMessage(toSave);
			});
		} else {
			Integer auxMessId = Integer.parseInt(messageId);
			Message toSave = this.messageService.findOneMesageById(auxMessId);
			assertTrue(!this.messageService.saveMessage(toSave).equals(null));
			};
		this.unauthenticate();
	}

	@DisplayName("Positive Save Message Test")
	@ParameterizedTest
	@CsvSource({ "Test text to save a Message, 100" })
	void positiveSaveTest(String text, Integer threadId) {

		this.authenticate("RAIMUNDOKARATE98");
		Message toSave = this.messageService.create(threadId);
		toSave.setText(text);
		assertTrue(!this.messageService.saveMessage(toSave).equals(null));
	}

	@DisplayName("Delete Message Test")
	@ParameterizedTest(name = "\"{0}\" Represents message´s id -> Messages are not valid to delete")
	@CsvSource({ "null, RAIMUNDOKARATE98", "0,RAIMUNDOKARATE98", "100, summoner4", "100, admin" })
	void deleteTest(String messageId, String username) {

		this.authenticate(username);

		if (messageId.equals("null")) {
			assertThrows(AssertionError.class, () -> {
				this.messageService.delete(null);
			});
		} else if (messageId.equals("0")) {
			Message toDelete = this.messageService.create(100);
			assertThrows(NullPointerException.class, () -> {
				this.messageService.delete(toDelete);
			});
		} else if (username.equals("summoner4")) {
			Message toDelete = this.messageService.findOneMesageById(Integer.parseInt(messageId));
			assertThrows(AssertionError.class, () -> {
				this.messageService.delete(toDelete);
			});
		} else {
			Message toDelete = this.messageService.findOneMesageById(Integer.parseInt(messageId));
			this.messageService.delete(toDelete);
			assertTrue(true);
		}
	}

	@DisplayName("Find messages by Thread Id")
	@ParameterizedTest(name = "\"{0}\"Represents Thread´s id to get messages by thread id")
	@CsvSource({ "0", "100" })
	void testFindByThreadId(int threadId) {

		if (threadId == 0) {
			assertThrows(AssertionError.class, () -> {
				this.threadService.findOne(threadId);
			});
		} else {
			Thread thread = this.threadService.findOne(threadId);
			assertTrue(!this.messageService.findByThread(thread).isEmpty());
		}
	}

}
