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
import org.springframework.samples.the_ionian_bookshelf.model.Build;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.model.User;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class BuildValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean= new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
		}
	
	@Test
	void shouldNotValidateWhenDescriptionAndTitleBlank() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Role r = new Role("Rol1", "Soy un rol de prueba ten paciencia", "https://www.youtube.com/");
		Champion c = new Champion("Cham1", "La descripción es algo superfluo sin cabida en una mente abierta",
				10., 5., null, 20., 50., r);
		Collection<Champion> mains = new ArrayList<Champion>();
		mains.add(c);
		User user = new User();
		user.setUsername("Pepin");
		user.setPassword("papin");
		Summoner summoner = new Summoner();
		summoner.setUser(user);
		summoner.setEmail("pru@gmail.com");
		summoner.setMains(mains);
		Build build = new Build("", "", false, new ArrayList<>(), c, null, null, summoner);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Build>> constraintViolations = validator.validate(build);
		List<ConstraintViolation<Build>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x -> list.add(x));
		assertThat(list).hasSize(4);

		for (ConstraintViolation<Build> violation : list) {
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
		Role r = new Role("Rol1", "Soy un rol de prueba ten paciencia", "https://www.youtube.com/");
		Champion c = new Champion("Cham1", "La descripción es algo superfluo sin cabida en una mente abierta",
				10., 5., null, 20., 50., r);
		Collection<Champion> mains = new ArrayList<Champion>();
		mains.add(c);
		User user = new User();
		user.setUsername("Pepin");
		user.setPassword("papin");
		Summoner summoner = new Summoner();
		summoner.setUser(user);
		summoner.setEmail("pru@gmail.com");
		summoner.setMains(mains);
		Build build = new Build("Build de testeooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo",
				"Soy una build con una descripción muy bonita, sí", false, new ArrayList<>(), c, null, null, summoner);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Build>> constraintViolations = validator.validate(build);
		List<ConstraintViolation<Build>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x -> list.add(x));
		assertThat(list).hasSize(1);

		for (ConstraintViolation<Build> violation : list) {
			assertThat(violation.getPropertyPath().toString()).isEqualTo("title");
			assertThat(violation.getMessage()).isEqualTo("size must be between 10 and 40");
		}
	}
	
	@Test
	void shouldNotValidateWhenChampionAndSummonerIsNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Build build = new Build("Build de testeo", "Soy una build con una descripción muy bonita, sí", false, new ArrayList<>(), null, null, null, null);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Build>> constraintViolations = validator.validate(build);
		List<ConstraintViolation<Build>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x -> list.add(x));
		assertThat(list).hasSize(2);

		for (ConstraintViolation<Build> violation : list) {
			if (violation.getPropertyPath().toString().contentEquals("champion")) {
				assertThat(violation.getPropertyPath().toString()).isEqualTo("champion");
				assertThat(violation.getMessage()).isEqualTo("must not be null");
			} else if (violation.getPropertyPath().toString().contentEquals("summoner")) {
				assertThat(violation.getPropertyPath().toString()).isEqualTo("summoner");
				assertThat(violation.getMessage()).isEqualTo("must not be null");
			} else {
				assertTrue(false);
			}
		}
	}
	
	@Test
	void shouldNotValidateWhenItemsAre7OrMore() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Role r = new Role("Rol1", "Soy un rol de prueba ten paciencia", "https://www.youtube.com/");
		Champion c = new Champion("Cham1", "La descripción es algo superfluo sin cabida en una mente abierta",
				10., 5., null, 20., 50., r);
		List<String> attributes = new ArrayList<>();
		attributes.add("34");
		attributes.add("80");
		attributes.add("80");
		List<Role> roles = new ArrayList<>();
		roles.add(r);
		Item item = new Item("test", "testeandoooooooooooooooooo", attributes, roles);
		Item item2 = new Item("test2", "testeandoooooooooooooooooo", attributes, roles);
		Item item3 = new Item("test3", "testeandoooooooooooooooooo", attributes, roles);
		Item item4 = new Item("test4", "testeandoooooooooooooooooo", attributes, roles);
		Item item5 = new Item("test5", "testeandoooooooooooooooooo", attributes, roles);
		Item item6 = new Item("test6", "testeandoooooooooooooooooo", attributes, roles);
		Item item7 = new Item("test7", "testeandoooooooooooooooooo", attributes, roles);
		List<Item> items = new ArrayList<Item>();
		items.add(item);
		items.add(item2);
		items.add(item3);
		items.add(item4);
		items.add(item5);
		items.add(item6);
		items.add(item7);
		Collection<Champion> mains = new ArrayList<Champion>();
		mains.add(c);
		User user = new User();
		user.setUsername("Pepin");
		user.setPassword("papin");
		Summoner summoner = new Summoner();
		summoner.setUser(user);
		summoner.setEmail("pru@gmail.com");
		summoner.setMains(mains);
		Build build = new Build("Build de testeo",
				"Soy una build con una descripción muy bonita, sí", false, items, c, null, null, summoner);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Build>> constraintViolations = validator.validate(build);
		List<ConstraintViolation<Build>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x -> list.add(x));
		assertThat(list).hasSize(1);

		for (ConstraintViolation<Build> violation : list) {
			assertThat(violation.getPropertyPath().toString()).isEqualTo("items");
			assertThat(violation.getMessage()).isEqualTo("size must be between 0 and 6");
		}
	}
}
