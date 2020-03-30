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
import org.springframework.samples.the_ionian_bookshelf.model.Branch;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.model.Rune;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class RuneValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean= new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
		}

	
	@Test
	void shouldNotValidateWhenDescriptionAndTitleBlank() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Branch branch = new Branch("Nombre","Descripcion del branch","http://www.miimagendebranch.com");
		String name = "";
		String desc = "";
		String node = "Key";
		Rune rune = new Rune(name, desc, branch, node);
		Validator validator= createValidator();
		Set<ConstraintViolation<Rune>> constraintViolations = validator.validate(rune);
		List<ConstraintViolation<Rune>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x->list.add(x));
		assertThat(list).hasSize(2);

		for(ConstraintViolation<Rune> violation : list) {
			if(violation.getPropertyPath().toString().contentEquals("description")) {
				if(violation.getMessage().contentEquals("must not be blank")){
					assertThat(violation.getPropertyPath().toString()) .isEqualTo("description");
					assertThat(violation.getMessage()).isEqualTo("must not be blank");
				}
		}
			else if(violation.getPropertyPath().toString() .contentEquals("name")){
						assertThat(violation.getPropertyPath().toString()) .isEqualTo("name");
						assertThat(violation.getMessage()).isEqualTo("must not be blank");
			}else {
				assertTrue(false);
			}
		}
	}
	
	
	@Test
	void shouldNotValidateWhenTitleIsSoBig() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Branch branch = new Branch("Nombre","Descripcion del branch","http://www.miimagendebranch.com");
		String name = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		String desc = "asdasdas";
		String node = "Key";
		Rune rune = new Rune(name, desc, branch, node);
		Validator validator= createValidator();
		Set<ConstraintViolation<Rune>> constraintViolations = validator.validate(rune);
		
		List<ConstraintViolation<Rune>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x->list.add(x));
		assertThat(list).hasSize(1);
		for(ConstraintViolation<Rune> violation : list) {
					assertThat(violation.getPropertyPath().toString()) .isEqualTo("name");
					assertThat(violation.getMessage()).isEqualTo("size must be between 0 and 30");
		}
	}
	
	@Test
	void shouldNotValidateWhenDescIsSoBig() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Branch branch = new Branch("Nombre","Descripcion del branch","http://www.miimagendebranch.com");
		String name = "nombre";
		String desc = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		String node = "Key";
		Rune rune = new Rune(name, desc, branch, node);
		Validator validator= createValidator();
		Set<ConstraintViolation<Rune>> constraintViolations = validator.validate(rune);
		List<ConstraintViolation<Rune>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x->list.add(x));
		assertThat(list).hasSize(1);
		for(ConstraintViolation<Rune> violation : list) {
					assertThat(violation.getPropertyPath().toString()) .isEqualTo("description");
					assertThat(violation.getMessage()).isEqualTo("size must be between 0 and 250");
		}
	}
	
	@Test
	void shouldNotValidateWhenBranchIsNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Branch branch = null;
		String name = "nombre";
		String desc = "descripción";
		String node = "Key";
		Rune rune = new Rune(name, desc, branch, node);
		Validator validator= createValidator();
		Set<ConstraintViolation<Rune>> constraintViolations = validator.validate(rune);
		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<Rune> violation=   constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()) .isEqualTo("branch");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
		}
	

	
	@Test
	void shouldNotValidateWhenNodeIsBlank() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Branch branch = new Branch("Nombre","Descripcion del branch","http://www.miimagendebranch.com");
		String name = "nombre";
		String desc = "descripción";
		String node = null;
		Rune rune = new Rune(name, desc, branch, node);
		Validator validator= createValidator();
		Set<ConstraintViolation<Rune>> constraintViolations = validator.validate(rune);
		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<Rune> violation=   constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()) .isEqualTo("node");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
		}
	

	@Test
	void shouldNotValidateWhenNodeMustNotMatchPattern() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Branch branch = new Branch("Nombre","Descripcion del branch","http://www.miimagendebranch.com");
		String name = "nombre";
		String desc = "descripción";
		String node = "nodo";
		Rune rune = new Rune(name, desc, branch, node);
		Validator validator= createValidator();
		Set<ConstraintViolation<Rune>> constraintViolations = validator.validate(rune);
		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<Rune> violation=   constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()) .isEqualTo("node");
		String patron = "^(Key|1|2|3)$";
		assertThat(violation.getMessage()).isEqualTo("must match \"^(Key|1|2|3)$\"");
				
	}
	
	
}