package org.springframework.samples.the_ionian_bookshelf.validators;

import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ChampionValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Champion.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Champion champion = (Champion) target;
		String name = champion.getName();
		String description = champion.getDescription();
		Double mana = champion.getMana();
		Double health = champion.getHealth();
		Double energy = champion.getEnergy();
		Double attack = champion.getAttack();
		Double speed = champion.getSpeed();
		Role role = champion.getRole();
		// name validation
		if (!StringUtils.hasLength(name)) {
			errors.rejectValue("name", "required", "must not be empty");
		}
		if(name.length()>20) {
			errors.rejectValue("name", "tooLongName", "The name is too long");
		}
		
		if (!StringUtils.hasLength(description)) {
			errors.rejectValue("description", "required", "must not be empty");
		}
		if(description.length()>250) {
			errors.rejectValue("description", "tooLongDesc", "The description is too long");
		}
		
		if(attack==null) { //Revisar, peta al hacer null attack
			errors.rejectValue("attack", "incorrectValue", "must not be null");
		}else if(attack< 0.0){
			errors.rejectValue("attack", "negativeValue", "must be a positive value");
		}
		
		if(health==null) {
			errors.rejectValue("health", "incorrectValue", "must not be null");
		}else if(health< 0.0){
			errors.rejectValue("health", "negativeValue", "must be a positive value");
		}
		
		
		
		
		
		if(speed==null) {
			errors.rejectValue("speed", "incorrectValue", "must not be null");
		}else if(speed< 0.0){
			errors.rejectValue("speed", "negativeValue", "must be a positive value");
		}
		
		
		if(mana!=null && energy==null && mana<0.0) {
			errors.rejectValue("mana", "incorrectValueMana", "Mana must not be less than 0");
		}
		
		
		if(energy!=null && mana==null && energy<0.0) {
			errors.rejectValue("energy", "incorrectValueEnergy", "Energy must not be less than 0");
		}
		
		
		if(role==null) {
			errors.rejectValue("role", "nullRole", "Role must not be null");
		}
		
		if(mana!=null && energy!=null) {
			errors.rejectValue("energy", "isNullValue", "A Champion must have mana or energy");
		}
		if(mana==null && energy==null) {
			errors.rejectValue("energy", "isNullValue", "A Champion must have mana or energy");
		}
		
		
	}

}

