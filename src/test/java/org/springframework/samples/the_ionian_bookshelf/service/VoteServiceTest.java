package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.the_ionian_bookshelf.model.Build;
import org.springframework.samples.the_ionian_bookshelf.model.Message;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.utilities.AbstractTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@EnableTransactionManagement
public class VoteServiceTest  {

	@Autowired
	protected VoteService voteService;

	@Autowired
	protected SummonerService summonerService;

	@Autowired
	protected MessageService messageService;
	
	@Autowired
	protected BuildService buildService;

	@Autowired
	protected ThreadService threadService;

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

//	@DisplayName("Delete by Thread ID")
//	@ParameterizedTest(name = "\"{0}\": Represents Thread's ID")
//	@CsvSource({ "0", "100" })
//	void deleteByThreadTest(Integer threadId) {
//
//		if (threadId == 0) {
//			assertThrows(AssertionError.class, () -> {
//				this.voteService.deleteByThreadId(threadId);
//			});
//		} else {
//			this.voteService.deleteByThreadId(threadId);
//		}
//
//	}

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

//	@DisplayName("Delete by Message ID")
//	@ParameterizedTest(name = "\"{0}\": Represents Message ID")
//	@CsvSource({ "0", "100" })
//	void deleteByMessageTest(Integer messageId) {
//
//		if (messageId == 0) {
//			assertThrows(AssertionError.class, () -> {
//				this.voteService.deleteByMessageId(messageId);
//			});
//		} else {
//			this.voteService.deleteByMessageId(messageId);
//		}
//
//	}

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
	
	@WithMockUser("RAIMUNDOKARATE98")
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

	@WithMockUser("RAIMUNDOKARATE98")
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
	
	@WithMockUser("RAIMUNDOKARATE98")
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

	@WithMockUser("RAIMUNDOKARATE98")
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
	
	
	//Vote Build
	

	@WithMockUser("summoner1")
	@DisplayName("Upvote a Build")
	@ParameterizedTest(name = "\"{0}\": Represents the id of the Build to upvote")
	@CsvSource({ "null", "0", "1" })
	void upVoteBuildTest(String buildId) {

		Integer id;
		if (buildId.equals("null")) {
			id = null;
		} else {
			id = Integer.parseInt(buildId);
		}


		if (id == null) {
			assertThrows(NullPointerException.class, () -> {
				this.voteService.createUpVoteByBuildId(id);
			});
	} else if (id.equals(0)) {
			assertThrows(AssertionError.class, () ->{ 
				this.voteService.createUpVoteByBuildId(id);
				});
		} else {
			Integer initialPunctuation = this.voteService.getPunctuationBuild(this.buildService.findBuildById(id));
			this.voteService.createUpVoteByBuildId(id);
			Integer finalPunctuation = this.voteService.getPunctuationBuild(this.buildService.findBuildById(id));
			assertEquals(initialPunctuation+1, finalPunctuation);
		}
	}

	@WithMockUser("summoner1")
	@DisplayName("Downvote a Build")
	@ParameterizedTest(name = "\"{0}\": Represents the id of the Build to downvote")
	@CsvSource({ "null", "0", "1" })
	void downVoteBuildTest(String buildId) {

		Integer id;
		if (buildId.equals("null")) {
			id = null;
		} else {
			id = Integer.parseInt(buildId);
		}

		if (id == null) {
			assertThrows(NullPointerException.class, () -> {
				this.voteService.createDownVoteByBuildId(id);
			});
		} else if (id.equals(0)) {
			assertThrows(AssertionError.class, () -> {
				this.voteService.createDownVoteByBuildId(id);
			});
		} else {
			Integer initialPunctuation = this.voteService.getPunctuationBuild(this.buildService.findBuildById(id));
			this.voteService.createDownVoteByBuildId(id);
			Integer finalPunctuation = this.voteService.getPunctuationBuild(this.buildService.findBuildById(id));
			assertEquals(initialPunctuation-1, finalPunctuation);
		}
	}
	@WithMockUser(username = "admin")
	@DisplayName("Delete by Build ID")
	@ParameterizedTest(name = "\"{0}\": Represents Build's ID")
	@CsvSource({ "0", "1" })
	void deleteByBuildTest(Integer buildId) {

		if (buildId == 0) {
			assertThrows(AssertionError.class, () -> {
				this.voteService.deleteByBuildId(buildId);
			});
		} else {
			this.voteService.deleteByBuildId(buildId);
		}

	}

	@DisplayName("Punctuation by Build ID")
	@ParameterizedTest(name = "\"{0}\": Represents Build's ID")
	@CsvSource({ "0", "1" })
	void punctuationByBuildTest(Integer buildId) {

		if (buildId == 0) {
			assertThrows(AssertionError.class, () -> {
				this.voteService.getPunctuationBuild(null);
			});
		} else {
			Build build = this.buildService.findBuildById(buildId);
			this.voteService.getPunctuationBuild(build);
		}

	}

	@DisplayName("Find by Build ID")
	@ParameterizedTest(name = "\"{0}\": Represents Build's ID")
	@CsvSource({ "0", "1" })
	void findByBuildTest(Integer buildId) {

		if (buildId == 0) {
			assertThrows(AssertionError.class, () -> {
				this.voteService.findByBuildId(0);
			});
		} else {
			this.voteService.findByBuildId(buildId);
		}

	}

}
