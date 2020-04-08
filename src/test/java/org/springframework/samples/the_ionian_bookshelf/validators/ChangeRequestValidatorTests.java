package org.springframework.samples.the_ionian_bookshelf.validators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.ChangeRequest;
import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.model.User;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ChangeRequestValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenDescriptionAndTitleBlank() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		List<String> attributes = new ArrayList<>();
		attributes.add("34");
		attributes.add("80");
		attributes.add("80");
		Role rol = new Role("rolTest", "testeoooooooooooooooo", "https://www.google.es");
		List<Role> roles = new ArrayList<>();
		roles.add(rol);
		Item item = new Item("test", "testeandoooooooooooooooooo", attributes, roles);
		List<String> changeItem = new ArrayList<>();
		changeItem.add("1");
		changeItem.add("1");
		changeItem.add("3");
		Collection<Champion> mains = new ArrayList<Champion>();
		Champion c = new Champion("Cham1", "La descripción es algo superfluo sin cabida en una mente abierta",
				10., 5., null, 20., 50., rol);
		mains.add(c);
		User user = new User();
		user.setUsername("Pepin");
		user.setPassword("papin");
		Summoner summoner = new Summoner();
		summoner.setUser(user);
		summoner.setEmail("pru@gmail.com");
		summoner.setMains(mains);
		ChangeRequest request = new ChangeRequest(null, item, "", "", null, changeItem, "PENDING", summoner , null);
		Validator validator = createValidator();
		Set<ConstraintViolation<ChangeRequest>> constraintViolations = validator.validate(request);
		List<ConstraintViolation<ChangeRequest>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x -> list.add(x));
		assertThat(list).hasSize(3);

		for (ConstraintViolation<ChangeRequest> violation : list) {
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
					assertThat(violation.getMessage()).isEqualTo("size must be between 10 and 40");
				}
			} else {
				assertTrue(false);
			}
		}
	}

	@Test
	void shouldNotValidateWhenTitleIsSoBig() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		List<String> attributes = new ArrayList<>();
		attributes.add("34");
		attributes.add("80");
		attributes.add("80");
		Role rol = new Role("rolTest", "testeoooooooooooooooo", "https://www.google.es");
		List<Role> roles = new ArrayList<>();
		roles.add(rol);
		Item item = new Item("test", "testeandoooooooooooooooooo", attributes, roles);
		List<String> changeItem = new ArrayList<>();
		changeItem.add("1");
		changeItem.add("1");
		changeItem.add("3");
		Collection<Champion> mains = new ArrayList<Champion>();
		Champion c = new Champion("Cham1", "La descripción es algo superfluo sin cabida en una mente abierta",
				10., 5., null, 20., 50., rol);
		mains.add(c);
		User user = new User();
		user.setUsername("Pepin");
		user.setPassword("papin");
		Summoner summoner = new Summoner();
		summoner.setUser(user);
		summoner.setEmail("pru@gmail.com");
		summoner.setMains(mains);
		ChangeRequest request = new ChangeRequest(null, item,
				"ChangeReqtttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt",
				"Soy la descripcion y debo tener al menos 20 años de edad.", null, changeItem, "PENDING", summoner, null);

		Validator validator = createValidator();
		Set<ConstraintViolation<ChangeRequest>> constraintViolations = validator.validate(request);
		List<ConstraintViolation<ChangeRequest>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x -> list.add(x));
		assertThat(list).hasSize(1);

		for (ConstraintViolation<ChangeRequest> violation : list) {
			assertThat(violation.getPropertyPath().toString()).isEqualTo("title");
			assertThat(violation.getMessage()).isEqualTo("size must be between 0 and 40");
		}
	}

	@Test
	void shouldNotValidateWhenStatusIsIncorrect() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		List<String> attributes = new ArrayList<>();
		attributes.add("34");
		attributes.add("80");
		attributes.add("80");
		Role rol = new Role("rolTest", "testeoooooooooooooooo", "https://www.google.es");
		List<Role> roles = new ArrayList<>();
		roles.add(rol);
		Item item = new Item("test", "testeandoooooooooooooooooo", attributes, roles);
		List<String> changeItem = new ArrayList<>();
		changeItem.add("1");
		changeItem.add("1");
		changeItem.add("3");
		Collection<Champion> mains = new ArrayList<Champion>();
		Champion c = new Champion("Cham1", "La descripción es algo superfluo sin cabida en una mente abierta",
				10., 5., null, 20., 50., rol);
		mains.add(c);
		User user = new User();
		user.setUsername("Pepin");
		user.setPassword("papin");
		Summoner summoner = new Summoner();
		summoner.setUser(user);
		summoner.setEmail("pru@gmail.com");
		summoner.setMains(mains);
		ChangeRequest request = new ChangeRequest(null, item, "request numero 1",
				"Soy la descripcion y debo tener al menos 20 años de edad.", null, changeItem, "p", summoner, null);

		Validator validator = createValidator();
		Set<ConstraintViolation<ChangeRequest>> constraintViolations = validator.validate(request);
		List<ConstraintViolation<ChangeRequest>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x -> list.add(x));
		assertThat(list).hasSize(1);

		for (ConstraintViolation<ChangeRequest> violation : list) {
			assertThat(violation.getPropertyPath().toString()).isEqualTo("status");
			assertThat(violation.getMessage()).isEqualTo("must match \"^(REJECTED|PENDING|ACCEPTED)$\"");
		}
	}

	@Test
	void shouldNotValidateWhenChangeRequestIsEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		ChangeRequest request = new ChangeRequest();

		Validator validator = createValidator();
		Set<ConstraintViolation<ChangeRequest>> constraintViolations = validator.validate(request);
		List<ConstraintViolation<ChangeRequest>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x -> list.add(x));
		assertThat(list).hasSize(4);

		for (ConstraintViolation<ChangeRequest> violation : list) {
			if (violation.getPropertyPath().toString().contentEquals("status")) {
				assertThat(violation.getPropertyPath().toString()).isEqualTo("status");
				assertThat(violation.getMessage()).isEqualTo("must not be blank");
			} else if (violation.getPropertyPath().toString().contentEquals("description")) {
				assertThat(violation.getPropertyPath().toString()).isEqualTo("description");
				assertThat(violation.getMessage()).isEqualTo("must not be blank");
			} else if (violation.getPropertyPath().toString().contentEquals("title")) {
				assertThat(violation.getPropertyPath().toString()).isEqualTo("title");
				assertThat(violation.getMessage()).isEqualTo("must not be blank");
			} else if (violation.getPropertyPath().toString().contentEquals("summoner")) {
				assertThat(violation.getPropertyPath().toString()).isEqualTo("summoner");
				assertThat(violation.getMessage()).isEqualTo("must not be null");
			}
		}
	}
}
