package org.springframework.samples.the_ionian_bookshelf.validators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ChampionValidatorTests{

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean= new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
		}

	
	@Test
	void shouldNotValidateWhenDescriptionAndTitleBlank() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Role tirador = new Role("Tirador","Descripcion del rol","http://www.miimagendetirador.coms");
		String name = "";
		String desc = "";
		Double health = 900.0;
		Double mana = 500.0;
		Double energy = null;
		Double attack = 1.2;
		Double speed = 1.0;
		Champion champion = new Champion(name, desc, health, mana, energy, attack, speed, tirador);
		Validator validator= createValidator();
		Set<ConstraintViolation<Champion>> constraintViolations = validator.validate(champion);
		List<ConstraintViolation<Champion>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x->list.add(x));
		assertThat(list).hasSize(2);

		for(ConstraintViolation<Champion> violation : list) {
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
		Role tirador = new Role("Tirador","Descripcion del rol","http://www.miimagendetirador.coms");
		String desc = "q";
		Double health = 100.0;
		Double mana = 500.0;
		Double energy = null;
		Double attack = 1.2;
		Double speed = 1.0;
		Champion champion = new Champion("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", desc, health, mana, energy, attack, speed, tirador);
		Validator validator= createValidator();
		Set<ConstraintViolation<Champion>> constraintViolations = validator.validate(champion);
		
		List<ConstraintViolation<Champion>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x->list.add(x));
		assertThat(list).hasSize(1);
		for(ConstraintViolation<Champion> violation : list) {
					assertThat(violation.getPropertyPath().toString()) .isEqualTo("name");
					assertThat(violation.getMessage()).isEqualTo("size must be between 0 and 20");
		}
	}
	
	@Test
	void shouldNotValidateWhenDescIsSoBig() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Role tirador = new Role("Tirador","Descripcion del rol","http://www.miimagendetirador.coms");
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
		Double health = 100.0;
		Double mana = 500.0;
		Double energy = null;
		Double attack = 1.2;
		Double speed = 1.0;
		Champion champion = new Champion("title", desc, health, mana, energy, attack, speed, tirador);
		Validator validator= createValidator();
		Set<ConstraintViolation<Champion>> constraintViolations = validator.validate(champion);
		List<ConstraintViolation<Champion>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x->list.add(x));
		assertThat(list).hasSize(1);
		for(ConstraintViolation<Champion> violation : list) {
					assertThat(violation.getPropertyPath().toString()) .isEqualTo("description");
					assertThat(violation.getMessage()).isEqualTo("size must be between 0 and 250");
		}
	}
	

	
	@Test
	void shouldNotValidateWhenHealthIsNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Role tirador = new Role("Tirador","Descripcion del rol","http://www.miimagendetirador.coms");
		String name = "Ashe";
		String desc = "descripcion";
		Double health = null;
		Double mana = 500.0;
		Double energy = null;
		Double attack = 1.2;
		Double speed = 1.0;
		Champion champion = new Champion(name, desc, health, mana, energy, attack, speed, tirador);
		Validator validator= createValidator();
		Set<ConstraintViolation<Champion>> constraintViolations = validator.validate(champion);
		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<Champion> violation=   constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()) .isEqualTo("health");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
		}
	
	
	@Test
	void shouldNotValidateWhenAttackIsNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Role tirador = new Role("Tirador","Descripcion del rol","http://www.miimagendetirador.coms");
		String name = "Ashe";
		String desc = "descripcion";
		Double health = 900.0;
		Double mana = 500.0;
		Double energy = null;
		Double attack = null;
		Double speed = 1.0;
		Champion champion = new Champion(name, desc, health, mana, energy, attack, speed, tirador);
		Validator validator= createValidator();
		Set<ConstraintViolation<Champion>> constraintViolations = validator.validate(champion);
		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<Champion> violation=   constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()) .isEqualTo("attack");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
		}
	
	@Test
	void shouldNotValidateWhenSpeedIsNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Role tirador = new Role("Tirador","Descripcion del rol","http://www.miimagendetirador.coms");
		String name = "Ashe";
		String desc = "descripcion";
		Double health = 900.0;
		Double mana = 500.0;
		Double energy = null;
		Double attack = 1.2;
		Double speed = null;
		Champion champion = new Champion(name, desc, health, mana, energy, attack, speed, tirador);
		Validator validator= createValidator();
		Set<ConstraintViolation<Champion>> constraintViolations = validator.validate(champion);
		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<Champion> violation=   constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("speed");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
		}
	

	
	@Test
	void shouldNotValidateWhenRoleIsNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		String name = "Ashe";
		String desc = "descripcion";
		Double health = 900.0;
		Double mana = 500.0;
		Double energy = null;
		Double attack = 1.2;
		Double speed = 1.2;
		Champion champion = new Champion(name, desc, health, mana, energy, attack, speed, null);
		Validator validator= createValidator();
		Set<ConstraintViolation<Champion>> constraintViolations = validator.validate(champion);
		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<Champion> violation=   constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()) .isEqualTo("role");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
		}
	
	@Test
	void shouldNotValidateWhenHealthSpeedAndAttackAreNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Role tirador = new Role("Tirador","Descripcion del rol","http://www.miimagendetirador.coms");
		String name = "Ashe";
		String desc = "descripcion";
		Double health = null;
		Double mana = 500.0;
		Double energy = null;
		Double attack = null;
		Double speed = null;
		Champion champion = new Champion(name, desc, health, mana, energy, attack, speed, tirador);
		Validator validator= createValidator();
		Set<ConstraintViolation<Champion>> constraintViolations = validator.validate(champion);
		List<ConstraintViolation<Champion>> list = new ArrayList<>();
		constraintViolations.stream().forEach(x->list.add(x));
		assertThat(list).hasSize(3);
		
		for(ConstraintViolation<Champion> violation : list) {
			if(violation.getPropertyPath().toString().contentEquals("health")) {
				assertThat(violation.getPropertyPath().toString()) .isEqualTo("health");
				assertThat(violation.getMessage()).isEqualTo("must not be null");
			}else if(violation.getPropertyPath().toString() .contentEquals("attack")){
				assertThat(violation.getPropertyPath().toString()) .isEqualTo("attack");
				assertThat(violation.getMessage()).isEqualTo("must not be null");
		
			}else if(violation.getPropertyPath().toString() .contentEquals("speed")){
				assertThat(violation.getPropertyPath().toString()) .isEqualTo("speed");
				assertThat(violation.getMessage()).isEqualTo("must not be null");
		
			}else {
				assertTrue(false);
			}
		}
	}
	
	
}
