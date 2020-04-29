package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.model.Message;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.utilities.AbstractTest;

@SpringBootTest
public class VoteServiceTest extends AbstractTest {

	@Autowired
	private VoteService voteService;

	@Autowired
	private SummonerService summonerService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private ThreadService threadService;

	@DisplayName("Find by Thread ID")
	@ParameterizedTest(name = "\"{0}\": Represents Thread's ID")
	@CsvSource({ "0", "100" })
	void findByThreadTest(Integer threadId) {

		if (threadId == 0) {
			assertThrows(AssertionError.class, () -> {
				this.voteService.findByThread(null);
			});
		} else {
			Thread thread = this.threadService.findOne(threadId);
			this.voteService.findByThread(thread);
		}

	}

	@DisplayName("Delete by Thread ID")
	@ParameterizedTest(name = "\"{0}\": Represents Thread's ID")
	@CsvSource({ "0", "100" })
	void deleteByThreadTest(Integer threadId) {

		if (threadId == 0) {
			assertThrows(AssertionError.class, () -> {
				this.voteService.deleteByThreadId(threadId);
			});
		} else {
			this.voteService.deleteByThreadId(threadId);
		}

	}

	@DisplayName("Puntuation by Thread ID")
	@ParameterizedTest(name = "\"{0}\": Represents Thread's ID")
	@CsvSource({ "0", "100" })
	void puntuationByThreadTest(Integer threadId) {

		if (threadId == 0) {
			assertThrows(AssertionError.class, () -> {
				this.voteService.getPuntuationThread(null);
			});
		} else {
			Thread thread = this.threadService.findOne(threadId);
			this.voteService.getPuntuationThread(thread);
		}

	}

	@DisplayName("Delete by Message ID")
	@ParameterizedTest(name = "\"{0}\": Represents Message ID")
	@CsvSource({ "0", "100" })
	void deleteByMessageTest(Integer messageId) {

		if (messageId == 0) {
			assertThrows(AssertionError.class, () -> {
				this.voteService.deleteByMessageId(messageId);
			});
		} else {
			this.voteService.deleteByMessageId(messageId);
		}

	}

	@DisplayName("Puntuation by Message ID")
	@ParameterizedTest(name = "\"{0}\": Represents Message's ID")
	@CsvSource({ "0", "100" })
	void puntuationByMessageTest(Integer messageId) {

		if (messageId == 0) {
			assertThrows(AssertionError.class, () -> {
				this.voteService.getPuntuationMessage(null);
			});
		} else {
			Message message = this.messageService.findOneMesageById(messageId);
			this.voteService.getPuntuationMessage(message);
		}

	}

	@DisplayName("Find by Message ID")
	@ParameterizedTest(name = "\"{0}\": Represents Message's ID")
	@CsvSource({ "0", "100" })
	void findByMessageTest(Integer messageId) {

		if (messageId == 0) {
			assertThrows(AssertionError.class, () -> {
				this.messageService.findOneMesageById(0);
			});
		} else {
			this.voteService.findByMessageId(messageId);
		}

	}

	@DisplayName("Upvote a Thread")
	@ParameterizedTest(name = "\"{0}\": Represents the id of the Thread to upvote")
	@CsvSource({ "null", "0", "100" })
	void upVoteThreadTest(String threadId) {

		Integer id;
		if (threadId.equals("null")) {
			id = null;
		} else {
			id = Integer.parseInt(threadId);
		}

		this.authenticate("RAIMUNDOKARATE98");

		if (id == null) {
			assertThrows(NullPointerException.class, () -> {
				this.voteService.createUpVoteByThreadId(id);
			});
		} else if (id.equals(0)) {
			assertThrows(AssertionError.class, () -> {
				this.voteService.createUpVoteByThreadId(id);
			});
		} else {
			this.voteService.createUpVoteByThreadId(id);
		}
	}

	@DisplayName("Downvote a Thread")
	@ParameterizedTest(name = "\"{0}\": Represents the id of the Thread to downvote")
	@CsvSource({ "null", "0", "100" })
	void upDownThreadTest(String threadId) {

		Integer id;
		if (threadId.equals("null")) {
			id = null;
		} else {
			id = Integer.parseInt(threadId);
		}

		this.authenticate("RAIMUNDOKARATE98");

		if (id == null) {
			assertThrows(NullPointerException.class, () -> {
				this.voteService.createDownVoteByThreadId(id);
			});
		} else if (id.equals(0)) {
			assertThrows(AssertionError.class, () -> {
				this.voteService.createDownVoteByThreadId(id);
			});
		} else {
			this.voteService.createDownVoteByThreadId(id);
		}
	}

	@DisplayName("Upvote a Message")
	@ParameterizedTest(name = "\"{0}\": Represents the id of the Message to upvote")
	@CsvSource({ "null", "0", "100" })
	void upVoteMessageTest(String messageId) {

		Integer id;
		if (messageId.equals("null")) {
			id = null;
		} else {
			id = Integer.parseInt(messageId);
		}

		this.authenticate("RAIMUNDOKARATE98");

		if (id == null) {
			assertThrows(NullPointerException.class, () -> {
				this.voteService.createUpVoteByMessageId(id);
			});
		} else if (id.equals(0)) {
			assertThrows(AssertionError.class, () -> {
				this.voteService.createUpVoteByMessageId(id);
			});
		} else {
			this.voteService.createUpVoteByMessageId(id);
		}
	}

	@DisplayName("Downvote a Message")
	@ParameterizedTest(name = "\"{0}\": Represents the id of the Message to downvote")
	@CsvSource({ "null", "0", "100" })
	void upDownMessageTest(String messageId) {

		Integer id;
		if (messageId.equals("null")) {
			id = null;
		} else {
			id = Integer.parseInt(messageId);
		}

		this.authenticate("RAIMUNDOKARATE98");

		if (id == null) {
			assertThrows(NullPointerException.class, () -> {
				this.voteService.createDownVoteByMessageId(id);
			});
		} else if (id.equals(0)) {
			assertThrows(AssertionError.class, () -> {
				this.voteService.createDownVoteByMessageId(id);
			});
		} else {
			this.voteService.createDownVoteByMessageId(id);
		}
	}

}
