package the_ionian_bookshelf.validators;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import the_ionian_bookshelf.model.Champion;

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
		// name validation
		if (!StringUtils.hasLength(name)) {
			errors.rejectValue("name", "required", "must not be empty");
		}
		
		if (!StringUtils.hasLength(description)) {
			errors.rejectValue("description", "required", "must not be empty");
		}
		
		if(health==null) {
			errors.rejectValue("health", "isNullValue", "must no be null");
		}
		if(attack==null) {
			errors.rejectValue("attack", "isNullValue", "must no be null");
		}
		if(speed==null) {
			errors.rejectValue("speed", "isNullValue", "must no be null");
		}
		if(mana==null && energy==null) {
			errors.rejectValue("mana", "isNullValue", "mana and energy cant no be null simultaneously");
		}

	}

}

