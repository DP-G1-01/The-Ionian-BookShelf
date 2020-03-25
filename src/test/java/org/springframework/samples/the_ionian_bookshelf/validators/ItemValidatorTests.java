package org.springframework.samples.the_ionian_bookshelf.validators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.samples.the_ionian_bookshelf.model.Role;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ItemValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean= new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
		}
	
	@Test
	void shouldNotValidateWhenTitleEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		List<String> attributes = new ArrayList<>();
		attributes.add("34");
		attributes.add("80");
		Role rol = new Role("rolTest", "testeoooooooooooooooo", "https://www.google.es");
		List<Role> roles = new ArrayList<>();
		roles.add(rol);
		Item item = new Item("", "Descripciónnnnnnnn", attributes, roles);
		Validator validator= createValidator();
		Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<Item> violation=   constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()) .isEqualTo("title");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
		}
	
	@Test
	void shouldNotValidateWhenDescriptionEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		List<String> attributes = new ArrayList<>();
		attributes.add("34");
		attributes.add("80");
		Role rol = new Role("rolTest", "testeoooooooooooooooo", "https://www.google.es");
		List<Role> roles = new ArrayList<>();
		roles.add(rol);
		Item item = new Item("Titulo", "", attributes, roles);
		Validator validator= createValidator();
		Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
		assertThat(constraintViolations).hasSize(2);
		Iterator<ConstraintViolation<Item>> iterator;
		iterator = constraintViolations.iterator();
		ConstraintViolation<Item> violation=  iterator.next();
		assertThat(violation.getPropertyPath().toString()) .isEqualTo("description");
		assertThat(violation.getMessage()).isEqualTo("size must be between 10 and 500");
		ConstraintViolation<Item> violation2=   iterator.next();
		assertThat(violation2.getPropertyPath().toString()) .isEqualTo("description");
		assertThat(violation2.getMessage()).isEqualTo("must not be blank");
		}
	
	@Test
	void shouldNotValidateWhenListsEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		List<String> attributes = new ArrayList<>();
		List<Role> roles = new ArrayList<>();
		Item item = new Item("Titulo", "Descripcionnnnnn", attributes, roles);
		Validator validator= createValidator();
		Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
		assertThat(constraintViolations).hasSize(4);
		Iterator<ConstraintViolation<Item>> iterator;
		iterator = constraintViolations.iterator();
		ConstraintViolation<Item> violation=  iterator.next();
		assertThat(violation.getPropertyPath().toString()) .isEqualTo("attributes");
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
		ConstraintViolation<Item> violation2=   iterator.next();
		assertThat(violation2.getPropertyPath().toString()) .isEqualTo("roles");
		assertThat(violation2.getMessage()).isEqualTo("must not be empty");
		ConstraintViolation<Item> violation3=   iterator.next();
		assertThat(violation3.getPropertyPath().toString()) .isEqualTo("roles");
		assertThat(violation3.getMessage()).isEqualTo("size must be between 1 and 3");
		ConstraintViolation<Item> violation4=   iterator.next();
		assertThat(violation4.getPropertyPath().toString()) .isEqualTo("attributes");
		assertThat(violation4.getMessage()).isEqualTo("size must be between 1 and 3");
		}
	
	@Test
	void shouldNotValidateWhenListsTooBigEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		List<String> attributes = new ArrayList<>();
		attributes.add("34");
		attributes.add("80");
		attributes.add("34");
		attributes.add("80");
		Role rol = new Role("rolTest", "testeoooooooooooooooo", "https://www.google.es");
		Role rol2 = new Role("rolTest", "testeoooooooooooooooo", "https://www.google.es");
		Role rol3 = new Role("rolTest", "testeoooooooooooooooo", "https://www.google.es");
		Role rol4 = new Role("rolTest", "testeoooooooooooooooo", "https://www.google.es");
		List<Role> roles = new ArrayList<>();
		roles.add(rol);
		roles.add(rol2);
		roles.add(rol3);
		roles.add(rol4);
		Item item = new Item("Titulo", "Descripcionnnnnn", attributes, roles);
		Validator validator= createValidator();
		Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
		assertThat(constraintViolations).hasSize(2);
		Iterator<ConstraintViolation<Item>> iterator;
		iterator = constraintViolations.iterator();
		ConstraintViolation<Item> violation=  iterator.next();
		assertThat(violation.getPropertyPath().toString()) .isEqualTo("attributes");
		assertThat(violation.getMessage()).isEqualTo("size must be between 1 and 3");
		ConstraintViolation<Item> violation2=   iterator.next();
		assertThat(violation2.getPropertyPath().toString()) .isEqualTo("roles");
		assertThat(violation2.getMessage()).isEqualTo("size must be between 1 and 3");
		}
}
