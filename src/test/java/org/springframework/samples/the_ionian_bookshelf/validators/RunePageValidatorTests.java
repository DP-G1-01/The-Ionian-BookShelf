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
import org.springframework.samples.the_ionian_bookshelf.model.RunePage;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class RunePageValidatorTests {
	
	private static final Branch mainBranch = new Branch("Nombre","Descripcion del branch","http://www.miimagendebranch.com");
	private static final Branch secondaryBranch = new Branch("Nombre2","Descripcion del branch2","http://www.miimagendebranch2.com");
	private static final Rune keyRune = new Rune("ExampleKeyRune", "Description", mainBranch, "Key");
	private static final Rune mainRune1 = new Rune("ExampleMainRune1", "Description", mainBranch, "1");
	private static final Rune mainRune2 = new Rune("ExampleMainRune2", "Description", mainBranch, "2");
	private static final Rune mainRune3 = new Rune("ExampleMainRune3", "Description", mainBranch, "3");
	private static final Rune secRune1 = new Rune("ExampleSecRune1", "Description", secondaryBranch, "1");
	private static final Rune secRune2 = new Rune("ExampleSecRune2", "Description", secondaryBranch, "2");
	private static final Rune secRune3 = new Rune("ExampleSecRune3", "Description", secondaryBranch, "3");
	private static final Summoner summoner = new Summoner();

	
//
//	private Validator createValidator() {
//		LocalValidatorFactoryBean localValidatorFactoryBean= new LocalValidatorFactoryBean();
//		localValidatorFactoryBean.afterPropertiesSet();
//
//		return localValidatorFactoryBean;
//		}
//	
//	@Test
//	void shouldNotValidateWhenNameBlank() {
//		LocaleContextHolder.setLocale(Locale.ENGLISH);
//		RunePage runePage = new RunePage("", summoner, mainBranch, secondaryBranch, keyRune, mainRune1, mainRune2, mainRune3, secRune1, secRune2);
//		Validator validator= createValidator();
//		Set<ConstraintViolation<RunePage>> constraintViolations = validator.validate(runePage);
//		List<ConstraintViolation<RunePage>> list = new ArrayList<>();
//		constraintViolations.stream().forEach(x->list.add(x));
//		assertThat(list).hasSize(1);
//
//		for(ConstraintViolation<RunePage> violation : list) {
//			if(violation.getPropertyPath().toString().contentEquals("name")) {
//				assertThat(violation.getMessage().toString()).isEqualTo("Name can not be empty");
//				}
//		}
//	}
	
//	
//	@Test
//	void shouldNotValidateWhenTitleIsSoBig() {
//		LocaleContextHolder.setLocale(Locale.ENGLISH);
//		Branch branch = new Branch("Nombre","Descripcion del branch","http://www.miimagendebranch.com");
//		String name = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
//		String desc = "asdasdas";
//		String node = "Key";
//		Rune rune = new Rune(name, desc, branch, node);
//		Validator validator= createValidator();
//		Set<ConstraintViolation<Rune>> constraintViolations = validator.validate(rune);
//		
//		List<ConstraintViolation<Rune>> list = new ArrayList<>();
//		constraintViolations.stream().forEach(x->list.add(x));
//		assertThat(list).hasSize(1);
//		for(ConstraintViolation<Rune> violation : list) {
//					assertThat(violation.getPropertyPath().toString()) .isEqualTo("name");
//					assertThat(violation.getMessage()).isEqualTo("size must be between 0 and 30");
//		}
//	}
//	
//	@Test
//	void shouldNotValidateWhenDescIsSoBig() {
//		LocaleContextHolder.setLocale(Locale.ENGLISH);
//		Branch branch = new Branch("Nombre","Descripcion del branch","http://www.miimagendebranch.com");
//		String name = "nombre";
//		String desc = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
//		String node = "Key";
//		Rune rune = new Rune(name, desc, branch, node);
//		Validator validator= createValidator();
//		Set<ConstraintViolation<Rune>> constraintViolations = validator.validate(rune);
//		List<ConstraintViolation<Rune>> list = new ArrayList<>();
//		constraintViolations.stream().forEach(x->list.add(x));
//		assertThat(list).hasSize(1);
//		for(ConstraintViolation<Rune> violation : list) {
//					assertThat(violation.getPropertyPath().toString()) .isEqualTo("description");
//					assertThat(violation.getMessage()).isEqualTo("size must be between 0 and 250");
//		}
//	}
//	
//	@Test
//	void shouldNotValidateWhenBranchIsNull() {
//		LocaleContextHolder.setLocale(Locale.ENGLISH);
//		Branch branch = null;
//		String name = "nombre";
//		String desc = "descripción";
//		String node = "Key";
//		Rune rune = new Rune(name, desc, branch, node);
//		Validator validator= createValidator();
//		Set<ConstraintViolation<Rune>> constraintViolations = validator.validate(rune);
//		assertThat(constraintViolations).hasSize(1);
//		ConstraintViolation<Rune> violation=   constraintViolations.iterator().next();
//		assertThat(violation.getPropertyPath().toString()) .isEqualTo("branch");
//		assertThat(violation.getMessage()).isEqualTo("must not be null");
//		}
//	
//
//	
//	@Test
//	void shouldNotValidateWhenNodeIsBlank() {
//		LocaleContextHolder.setLocale(Locale.ENGLISH);
//		Branch branch = new Branch("Nombre","Descripcion del branch","http://www.miimagendebranch.com");
//		String name = "nombre";
//		String desc = "descripción";
//		String node = null;
//		Rune rune = new Rune(name, desc, branch, node);
//		Validator validator= createValidator();
//		Set<ConstraintViolation<Rune>> constraintViolations = validator.validate(rune);
//		assertThat(constraintViolations).hasSize(1);
//		ConstraintViolation<Rune> violation=   constraintViolations.iterator().next();
//		assertThat(violation.getPropertyPath().toString()) .isEqualTo("node");
//		assertThat(violation.getMessage()).isEqualTo("must not be blank");
//		}
//	
//
//	@Test
//	void shouldNotValidateWhenNodeMustNotMatchPattern() {
//		LocaleContextHolder.setLocale(Locale.ENGLISH);
//		Branch branch = new Branch("Nombre","Descripcion del branch","http://www.miimagendebranch.com");
//		String name = "nombre";
//		String desc = "descripción";
//		String node = "nodo";
//		Rune rune = new Rune(name, desc, branch, node);
//		Validator validator= createValidator();
//		Set<ConstraintViolation<Rune>> constraintViolations = validator.validate(rune);
//		assertThat(constraintViolations).hasSize(1);
//		ConstraintViolation<Rune> violation=   constraintViolations.iterator().next();
//		assertThat(violation.getPropertyPath().toString()) .isEqualTo("node");
//		String patron = "^(Key|1|2|3)$";
//		assertThat(violation.getMessage()).isEqualTo("must match \"^(Key|1|2|3)$\"");
//				
//	}
//	
	
}