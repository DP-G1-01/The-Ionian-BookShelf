package org.springframework.samples.the_ionian_bookshelf.validators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ThreadValidatorTests {
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean= new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
		}
	
	@Test
	void shouldNotValidateWhenDescriptionAndTitleBlank() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Thread thread = new Thread("", "");
		Validator validator = createValidator();
		Set<ConstraintViolation<Thread>> constraintViolations = validator.validate(thread);
		List<ConstraintViolation<Thread>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x -> list.add(x));
		assertThat(list).hasSize(4);

		for (ConstraintViolation<Thread> violation : list) {
			if (violation.getPropertyPath().toString().contentEquals("description")) {
				if (violation.getMessage().contentEquals("must not be blank")) {
					assertThat(violation.getPropertyPath().toString()).isEqualTo("description");
					assertThat(violation.getMessage()).isEqualTo("must not be blank");
				} else {
					assertThat(violation.getPropertyPath().toString()).isEqualTo("description");
					assertThat(violation.getMessage()).isEqualTo("size must be between 20 and 500");
				}
			} else if (violation.getPropertyPath().toString().contentEquals("title")) {
				if (violation.getMessage().contentEquals("must not be blank")) {
					assertThat(violation.getPropertyPath().toString()).isEqualTo("title");
					assertThat(violation.getMessage()).isEqualTo("must not be blank");
				} else {
					assertThat(violation.getPropertyPath().toString()).isEqualTo("title");
					assertThat(violation.getMessage()).isEqualTo("size must be between 5 and 50");
				}
			} else {
				assertTrue(false);
			}
		}
	}

	@Test
	void shouldNotValidateWhenTitleIsSoBig() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Thread thread = new Thread("Titulo de Hilo demasiado grande no debe superar los 50 caracteres", 
				"La descripcion del hilo debe de tener entre 20 y 500 caracteres");
		Validator validator = createValidator();
		Set<ConstraintViolation<Thread>> constraintViolations = validator.validate(thread);
		List<ConstraintViolation<Thread>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x -> list.add(x));
		assertThat(list).hasSize(1);

		for (ConstraintViolation<Thread> violation : list) {
			assertThat(violation.getPropertyPath().toString()).isEqualTo("title");
			assertThat(violation.getMessage()).isEqualTo("size must be between 5 and 50");
		}
	}

	@Test
	void shouldNotValidateWhenTitleIsSoSmall() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Thread thread = new Thread("NO", 
				"La descripcion del hilo debe de tener entre 20 y 500 caracteres");
		Validator validator = createValidator();
		Set<ConstraintViolation<Thread>> constraintViolations = validator.validate(thread);
		List<ConstraintViolation<Thread>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x -> list.add(x));
		assertThat(list).hasSize(1);

		for (ConstraintViolation<Thread> violation : list) {
			assertThat(violation.getPropertyPath().toString()).isEqualTo("title");
			assertThat(violation.getMessage()).isEqualTo("size must be between 5 and 50");
		}
	}
	
	@Test
	void shouldNotValidateWhenDescriptionIsSoBig() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Thread thread = new Thread("Titulo de Hilo", 
				"La descripcion del hilo debe de tener entre 20 y 500 caracteres."
				+ "Esta descripcion es demasiado largaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		Validator validator = createValidator();
		Set<ConstraintViolation<Thread>> constraintViolations = validator.validate(thread);
		List<ConstraintViolation<Thread>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x -> list.add(x));
		assertThat(list).hasSize(1);

		for (ConstraintViolation<Thread> violation : list) {
			assertThat(violation.getPropertyPath().toString()).isEqualTo("description");
			assertThat(violation.getMessage()).isEqualTo("size must be between 20 and 500");
		}
	}

	@Test
	void shouldNotValidateWhenDescriptionIsSoSmall() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Thread thread = new Thread("Titulo de Hilo", 
				"Descripcion corta");
		Validator validator = createValidator();
		Set<ConstraintViolation<Thread>> constraintViolations = validator.validate(thread);
		List<ConstraintViolation<Thread>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x -> list.add(x));
		assertThat(list).hasSize(1);

		for (ConstraintViolation<Thread> violation : list) {
			assertThat(violation.getPropertyPath().toString()).isEqualTo("description");
			assertThat(violation.getMessage()).isEqualTo("size must be between 20 and 500");
		}
	}
}