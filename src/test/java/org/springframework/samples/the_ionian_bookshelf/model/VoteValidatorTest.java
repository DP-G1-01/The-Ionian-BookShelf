//package org.springframework.samples.the_ionian_bookshelf.model;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//import java.util.Locale;
//import java.util.Set;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.Validator;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.samples.the_ionian_bookshelf.service.MessageService;
//import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
//import org.springframework.samples.the_ionian_bookshelf.service.ThreadService;
//import org.springframework.samples.the_ionian_bookshelf.service.VoteService;
//import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
//
//@SpringBootTest
//public class VoteValidatorTest {
//
//	@Autowired
//	private VoteService voteService;
//
//	@Autowired
//	private SummonerService summonerService;
//
//	@Autowired
//	private MessageService messageService;
//
//	@Autowired
//	private ThreadService threadService;
//
//	private Validator createValidator() {
//		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
//		localValidatorFactoryBean.afterPropertiesSet();
//		LocaleContextHolder.setLocale(Locale.ENGLISH);
//		return localValidatorFactoryBean;
//	}
//
//	@DisplayName("Vote's Summoner validator")
//	@ParameterizedTest(name = "\"{0}\": Represents the summoner's ID of the Vote")
//	@CsvSource({ "0", "5" })
//	void validateSummoner(Integer summonerId) {
//
//		Summoner voter;
//		Message message = this.messageService.findOneMesageById(100);
//		if (summonerId != 0) {
//			voter = this.summonerService.findOne(summonerId);
//			Vote toTest = new Vote(voter, null, null, message, true);
//			if (!toTest.getVoter().equals(null)) {
//				assertTrue(true);
//			}
//		} else {
//			Vote toTest = new Vote(null, null, null, message, true);
//			assertFalse(toTest.getVoter() != null);
//		}
//	}
//
//	@DisplayName("Vote Constraints validator")
//	@ParameterizedTest(name = "\"{0}\": Represents the test to perform")
//	@CsvSource({ "Thread and Message null, 0, 0", "Thread not null and Message null, 100, 0",
//			"Thread null and Message not null, 0, 100", "Thread and Message not null, 100, 100" })
//	void validateVoteConstraints(String text, Integer threadId, Integer messageId) {
//
//		Summoner voter = this.summonerService.findOne(5);
//		Vote toTest;
//		if (threadId == 0 && messageId == 0) {
//			toTest = new Vote(voter, null, null, null, true);
//			assertFalse((toTest.getThread() != null || toTest.getMessage() != null)
//					&& ((toTest.getThread() != null && toTest.getMessage() == null)
//							|| (toTest.getThread() == null && toTest.getMessage() != null)));
//		} else if (threadId != 0 && messageId == 0) {
//			Thread thread = this.threadService.findOne(threadId);
//			toTest = new Vote(voter, null, thread, null, true);
//			assertTrue((toTest.getThread() != null || toTest.getMessage() != null)
//					&& ((toTest.getThread() != null && toTest.getMessage() == null)
//							|| (toTest.getThread() == null && toTest.getMessage() != null)));
//		} else if (threadId == 0 && messageId != 0) {
//			Message message = this.messageService.findOneMesageById(messageId);
//			toTest = new Vote(voter, null, null, message, true);
//			assertTrue((toTest.getThread() != null || toTest.getMessage() != null)
//					&& ((toTest.getThread() != null && toTest.getMessage() == null)
//							|| (toTest.getThread() == null && toTest.getMessage() != null)));
//		} else {
//			Thread thread = this.threadService.findOne(threadId);
//			Message message = this.messageService.findOneMesageById(messageId);
//			toTest = new Vote(voter, null, thread, message, true);
//			assertFalse((toTest.getThread() != null || toTest.getMessage() != null)
//					&& ((toTest.getThread() != null && toTest.getMessage() == null)
//							|| (toTest.getThread() == null && toTest.getMessage() != null)));
//		}
//	}
//}
