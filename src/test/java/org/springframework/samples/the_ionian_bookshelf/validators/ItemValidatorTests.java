package org.springframework.samples.the_ionian_bookshelf.validators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
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
	void shouldNotValidateWhenDescriptionAndTitleBlank() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		List<String> attributes = new ArrayList<>();
		attributes.add("34");
		attributes.add("80");
		Role rol = new Role("rolTest", "testeoooooooooooooooo", "https://www.google.es");
		List<Role> roles = new ArrayList<>();
		roles.add(rol);
		Item item = new Item("", "", attributes, roles);
		Validator validator= createValidator();
		Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
		List<ConstraintViolation<Item>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x->list.add(x));
		assertThat(list).hasSize(3);

		for(ConstraintViolation<Item> violation : list) {
			if(violation.getPropertyPath().toString().contentEquals("description")) {
				if(violation.getMessage().contentEquals("must not be blank")){
					assertThat(violation.getPropertyPath().toString()) .isEqualTo("description");
					assertThat(violation.getMessage()).isEqualTo("must not be blank");
				}else {
					assertThat(violation.getPropertyPath().toString()) .isEqualTo("description");
					assertThat(violation.getMessage()).isEqualTo("size must be between 10 and 500");
				}
		}
			else if(violation.getPropertyPath().toString() .contentEquals("title")){
						assertThat(violation.getPropertyPath().toString()) .isEqualTo("title");
						assertThat(violation.getMessage()).isEqualTo("must not be blank");
			}else {
				assertTrue(false);
			}
		}
	}
	
	@Test
	void shouldNotValidateWhenTitleIsSoBig() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		List<String> attributes = new ArrayList<>();
		attributes.add("34");
		Role rol = new Role("rolTestttttt", "testeoooooooooooooooo", "https://www.google.es");
		List<Role> roles = new ArrayList<>();
		roles.add(rol);
		Item item = new Item("ItemTestttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt\"\r\n" + 
				"				+ \"ttttttttttttt", "testeoooooooooooooooo", attributes, roles);
		Validator validator= createValidator();
		Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
		List<ConstraintViolation<Item>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x->list.add(x));
		assertThat(list).hasSize(1);

		for(ConstraintViolation<Item> violation : list) {
					assertThat(violation.getPropertyPath().toString()) .isEqualTo("title");
					assertThat(violation.getMessage()).isEqualTo("size must be between 0 and 60");
		}
	}
	
	@Test
	void shouldNotValidateWhenListsEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		List<String> attributes = new ArrayList<>();
		List<Role> roles = new ArrayList<>();
		Item item = new Item("Titulo", "Descripcionnnnnn", attributes, roles);
		Validator validator= createValidator();
		Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
		List<ConstraintViolation<Item>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x->list.add(x));
		assertThat(list).hasSize(4);
		
		for(ConstraintViolation<Item> violation : list) {
			if(violation.getPropertyPath().toString().contentEquals("roles")) {
				if(violation.getMessage().contentEquals("must not be empty")){
					assertThat(violation.getPropertyPath().toString()) .isEqualTo("roles");
					assertThat(violation.getMessage()).isEqualTo("must not be empty");
				}else {
					assertThat(violation.getPropertyPath().toString()) .isEqualTo("roles");
					assertThat(violation.getMessage()).isEqualTo("size must be between 1 and 3");
				}
			}else if(violation.getPropertyPath().toString() .contentEquals("attributes")){
					if(violation.getMessage().contentEquals("must not be empty")){
						assertThat(violation.getPropertyPath().toString()) .isEqualTo("attributes");
						assertThat(violation.getMessage()).isEqualTo("must not be empty");
					}else {
						assertThat(violation.getPropertyPath().toString()) .isEqualTo("attributes");
						assertThat(violation.getMessage()).isEqualTo("size must be between 1 and 3");
				}
			}else {
				assertTrue(false);
			}
		}
	}
	
	@Test
	void shouldNotValidateWhenListsTooBig() {
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
		List<ConstraintViolation<Item>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x->list.add(x));
		assertThat(list).hasSize(2);
		
		for(ConstraintViolation<Item> violation : list) {
			if(violation.getPropertyPath().toString().contentEquals("roles")) {
					assertThat(violation.getPropertyPath().toString()) .isEqualTo("roles");
					assertThat(violation.getMessage()).isEqualTo("size must be between 1 and 3");
				}
			else if(violation.getPropertyPath().toString() .contentEquals("attributes")){
						assertThat(violation.getPropertyPath().toString()) .isEqualTo("attributes");
						assertThat(violation.getMessage()).isEqualTo("size must be between 1 and 3");
			}else {
				assertTrue(false);
			}
		}
	}
	
	@Test
	void shouldNotValidateWhenItemIsEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Item item = new Item();
		Validator validator= createValidator();
		Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
		List<ConstraintViolation<Item>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x->list.add(x));
		assertThat(list).hasSize(4);
		
		for(ConstraintViolation<Item> violation : list) {
			if(violation.getPropertyPath().toString().contentEquals("roles")) {
				assertThat(violation.getPropertyPath().toString()) .isEqualTo("roles");
				assertThat(violation.getMessage()).isEqualTo("must not be empty");
			}else if(violation.getPropertyPath().toString() .contentEquals("attributes")){
				assertThat(violation.getPropertyPath().toString()) .isEqualTo("attributes");
				assertThat(violation.getMessage()).isEqualTo("must not be empty");
			}else if(violation.getPropertyPath().toString() .contentEquals("description")){
				assertThat(violation.getPropertyPath().toString()) .isEqualTo("description");
				assertThat(violation.getMessage()).isEqualTo("must not be blank");
			}else if(violation.getPropertyPath().toString() .contentEquals("title")){
				assertThat(violation.getPropertyPath().toString()) .isEqualTo("title");
				assertThat(violation.getMessage()).isEqualTo("must not be blank");
			}else {
				assertTrue(false);
			}
		}
	}
}