package org.springframework.samples.the_ionian_bookshelf.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class ReviewerValidatorTest {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		return localValidatorFactoryBean;
	}

	@DisplayName("Reviewer's email validator")
	@ParameterizedTest(name = "\"{0}\": Reviewer´s email´s length should be between 1 and 40")
	@CsvSource({ "noEmail", "reviewer5@gmail.com" })
	void validateEmail(String email) {

		User reviewer5 = new User();
		reviewer5.setUsername("reviewer5");
		reviewer5.setPassword("reviewer5");
		reviewer5.setEnabled(true);
		Reviewer toTest = new Reviewer();
		toTest.setUser(reviewer5);
		if (email.equals("noEmail")) {
			toTest.setEmail("");
		} else {
			toTest.setEmail(email);
		}
		if (toTest.getEmail().length() > 0 && toTest.getEmail().length() <= 40) {
			assertTrue(true);
		} else {
			Validator validator = createValidator();
			Set<ConstraintViolation<Reviewer>> constraintViolations = validator.validate(toTest);
			ConstraintViolation<Reviewer> violation = constraintViolations.iterator().next();

			assertThat(violation.getPropertyPath().toString().equals("email"));
			assertThat(violation.getMessage().contentEquals("blank"));
		}
	}

	@DisplayName("Reviewer's username validator")
	@ParameterizedTest(name = "\"{0}\": Reviewer´s username´s length should be between 5 and 32")
	@CsvSource({ "noUsername", "negative", "reviewer5" })
	void validateUsername(String username) {

		User reviewer5 = new User();
		if (username.equals("noUsername")) {
			reviewer5.setUsername("");
		} else if (username.equals("negative")) {
			reviewer5.setUsername("less");
		} else {
			reviewer5.setUsername(username);
		}
		reviewer5.setPassword("reviewer5");
		reviewer5.setEnabled(true);
		Reviewer toTest = new Reviewer();
		toTest.setUser(reviewer5);
		toTest.setEmail("reviewer5@gmail.com");
		if (toTest.getUser().getUsername().length() >= 5 && toTest.getUser().getUsername().length() <= 32) {
			assertTrue(true);
		} else {
			Validator validator = createValidator();
			Set<ConstraintViolation<Reviewer>> constraintViolations = validator.validate(toTest);
			ConstraintViolation<Reviewer> violation = constraintViolations.iterator().next();

			assertThat(violation.getPropertyPath().toString().equals("username"));
			assertThat(violation.getMessage().contentEquals("blank") || violation.getMessage().contentEquals("size"));
		}
	}

	@DisplayName("Reviewer's password validator")
	@ParameterizedTest(name = "\"{0}\": Reviewer´s password´s length should be between 5 and 32")
	@CsvSource({ "noPassword", "negative", "reviewer5" })
	void validatePassword(String password) {

		User reviewer5 = new User();
		reviewer5.setUsername("reviewer5");
		reviewer5.setEnabled(true);
		if (password.equals("noPassword")) {
			reviewer5.setPassword("");
		} else if (password.equals("negative")) {
			reviewer5.setPassword("less");
		} else {
			reviewer5.setPassword(password);
		}
		Reviewer toTest = new Reviewer();
		toTest.setUser(reviewer5);
		toTest.setEmail("reviewer5@gmail.com");
		if (toTest.getUser().getPassword().length() >= 5 && toTest.getUser().getPassword().length() <= 32) {
			assertTrue(true);
		} else {
			Validator validator = createValidator();
			Set<ConstraintViolation<Reviewer>> constraintViolations = validator.validate(toTest);
			ConstraintViolation<Reviewer> violation = constraintViolations.iterator().next();

			assertThat(violation.getPropertyPath().toString().equals("password"));
			assertThat(violation.getMessage().contentEquals("blank") || violation.getMessage().contentEquals("size"));
		}
	}

}
