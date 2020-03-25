package org.springframework.samples.theionianbookshelf.validators;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import the_ionian_bookshelf.model.Champion;
import the_ionian_bookshelf.model.Item;
import the_ionian_bookshelf.model.Role;

public class ChampionValidatorTests{

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean= new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
		}
	
	@Test
	void shouldNotValidateWhenTitleEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Role tirador = new Role("Tirador","Descripcion del rol","http://www.miimagendetirador.coms");
		String name = "";
		String desc = "asdasdasd";
		Double health = 900.0;
		Double mana = 500.0;
		Double energy = 0.0;
		Double attack = 1.2;
		Double speed = 1.0;
		Champion champion = new Champion(name, desc, health, mana, energy, attack, speed, tirador);
		Validator validator= createValidator();
		Set<ConstraintViolation<Champion>> constraintViolations = validator.validate(champion);
		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<Champion> violation=   constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()) .isEqualTo("name");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
		}
	
	@Test
	void shouldNotValidateWhenDescriptionEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Role tirador = new Role("Tirador","Descripcion del rol","http://www.miimagendetirador.coms");
		String name = "Ashe";
		String desc = "";
		Double health = 900.0;
		Double mana = 500.0;
		Double energy = 0.0;
		Double attack = 1.2;
		Double speed = 1.0;
		Champion champion = new Champion(name, desc, health, mana, energy, attack, speed, tirador);
		Validator validator= createValidator();
		Set<ConstraintViolation<Champion>> constraintViolations = validator.validate(champion);
		assertThat(constraintViolations).hasSize(1);
		ConstraintViolation<Champion> violation=   constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()) .isEqualTo("description");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
		}
	
	@Test
	void shouldNotValidateWhenHealthIsNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Role tirador = new Role("Tirador","Descripcion del rol","http://www.miimagendetirador.coms");
		String name = "Ashe";
		String desc = "descripcion";
		Double health = null;
		Double mana = 500.0;
		Double energy = 0.0;
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
		Double energy = 0.0;
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
		Double energy = 0.0;
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
		Double energy = 0.0;
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
	
	
}
