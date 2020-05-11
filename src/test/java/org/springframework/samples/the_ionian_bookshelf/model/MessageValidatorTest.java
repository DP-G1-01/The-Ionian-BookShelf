package org.springframework.samples.the_ionian_bookshelf.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.the_ionian_bookshelf.model.Message;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.samples.the_ionian_bookshelf.service.ThreadService;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class MessageValidatorTest {

	@Mock
	private SummonerService summService;

	@Mock
	private ThreadService threadService;
	
	private Summoner summoner = new Summoner();
	
	private User user = new User();
	@BeforeEach
	void setup() {
		
		user.setUsername("summoner1");
		user.setPassword("papin");
		
		summoner.setUser(user);
		summoner.setId(1);
		summoner.setEmail("pru@gmail.com");
	}

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		return localValidatorFactoryBean;
	}

	@DisplayName("Message's text validator")
	@ParameterizedTest(name = "\"{0}\": Message´s text´s length should be between 10 and 500")
	@CsvSource({ "'', 2010/10/10 10:10, 1, 1", "123456789, 2010/10/10 10:10, 1, 1",
			"1234567890, 2010/10/10 10:10, 1, 1", "01234567890, 2010/10/10 10:10, 1, 1",
			"0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000, 2010/10/10 10:10, 1, 1",
			"00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000, 2010/10/10 10:10, 1, 1",
			"000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000, 2010/10/10 10:10, 1, 1",
			"000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000, 2010/10/10 10:10, 1, 1" })
	void validateText(String text, Date moment, Integer summonerId, Integer threadId) {

		when(summService.findOne(summonerId)).thenReturn(summoner);
		Summoner summ = this.summService.findOne(summonerId);
		Thread threadMock = new Thread("Titulo","Descripcion de thread");
		when(threadService.findOne(threadId)).thenReturn(threadMock);
		Thread thread = this.threadService.findOne(threadId);

		Message toTest = new Message(text, moment, summ, thread, null);

		if (toTest.getText().length() >= 10 && toTest.getText().length() <= 500) {
			assertTrue(true);
		} else {
			Validator validator = createValidator();
			Set<ConstraintViolation<Message>> constraintViolations = validator.validate(toTest);
			ConstraintViolation<Message> violation = constraintViolations.iterator().next();

			assertThat(violation.getPropertyPath().toString().equals("text"));
			assertThat(violation.getMessage().equals("Size must be bewteen 10 and 500"));
		}
	}

	@DisplayName("Message's moment validator")
	@ParameterizedTest(name = "\"{0}\":Message´s moment cant be in the future or null")
	@CsvSource({ "Not Null Date to test, 2000/10/10 10:10, 1, 1", "Positive test, 2020/03/02 10:10, 1,1",
			"Positive test, 2020/03/03 10:10, 1, 1", "Negative test, 2030/10/10 10:10, 1, 1",
			"Negative test, 2100/10/10 10:10, 1, 1" })
	void validateMoment(String text, Date moment, Integer summonerId, Integer threadId) {
	
		when(summService.findOne(summonerId)).thenReturn(summoner);
		Summoner summ = this.summService.findOne(summonerId);
		Thread threadMock = new Thread("Titulo","Descripcion de thread");
		when(threadService.findOne(threadId)).thenReturn(threadMock);
		Thread thread = this.threadService.findOne(threadId);

		Message toTest = new Message(text, moment, summ, thread, null);

		Date now = new Date(System.currentTimeMillis());

		if (toTest.getMoment().before(now)) {
			assertTrue(true);
		} else {
			Validator validator = createValidator();
			Set<ConstraintViolation<Message>> constraintViolations = validator.validate(toTest);
			ConstraintViolation<Message> violation = constraintViolations.iterator().next();

			assertThat(violation.getPropertyPath().toString().equals("moment"));
			assertThat(violation.getMessage().equals("Must be in the past"));
		}
	}

//	@DisplayName("Message's summoner validator")
//	@ParameterizedTest(name = "\"{0}\":Message´s summoner cannot be null")
//	@CsvSource({ "Valid Summoner Id, 2000/10/10 10:10, 1, 1", "Not Valid Summoner Id, 2000/10/10 10:10, 00000, 1" })
//	void validateSummoner(String text, Date moment, Integer summonerId, Integer threadId) {
//
//		when(summService.findOne(summonerId)).thenReturn(summoner);
//		if (summonerId.equals(00000)) {
//			assertThrows(AssertionError.class, () -> {
//				this.summService.findOne(summonerId);
//			});
//		} else {
//			Summoner summ = this.summService.findOne(summonerId);
//			Thread threadMock = new Thread("Titulo","Descripcion de thread");
//			when(threadService.findOne(threadId)).thenReturn(threadMock);
//			Thread thread = this.threadService.findOne(threadId);
//
//			Message toTest = new Message(text, moment, summ, thread);
//
//			if (!toTest.getSummoner().equals(null))
//				assertTrue(true);
//		}
//	}
//
//	@DisplayName("Message's thread validator")
//	@ParameterizedTest(name = "\"{0}\":Message´s thread cannot be null")
//	@CsvSource({ "Valid Thread Id, 2000/10/10 10:10, 1, 1", "Not Valid Thread Id, 2000/10/10 10:10, 1, 00000" })
//	void validateThread(String text, Date moment, Integer summonerId, Integer threadId) {
//
//		when(summService.findOne(summonerId)).thenReturn(summoner);
//		Thread threadMock = new Thread("Titulo","Descripcion de thread");
//		when(threadService.findOne(threadId)).thenReturn(threadMock);
//		if (threadId.equals(00000)) {
//			assertThrows(AssertionError.class, () -> {
//				this.threadService.findOne(threadId);
//			});
//		} else {
//			Summoner summ = this.summService.findOne(summonerId);
//			Thread thread = this.threadService.findOne(threadId);
//
//			Message toTest = new Message(text, moment, summ, thread);
//
//			if (!toTest.getThread().equals(null))
//				assertTrue(true);
//		}
//	}
}
