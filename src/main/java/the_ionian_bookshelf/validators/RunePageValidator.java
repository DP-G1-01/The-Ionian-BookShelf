package the_ionian_bookshelf.validators;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import the_ionian_bookshelf.model.Branch;
import the_ionian_bookshelf.model.Rune;
import the_ionian_bookshelf.model.RunePage;
import the_ionian_bookshelf.model.Summoner;

public class RunePageValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return RunePage.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RunePage runePage = (RunePage) target;
		String name = runePage.getName();
		Summoner summoner = runePage.getSummoner();
		Branch mainBranch = runePage.getMainBranch();
		Branch secondaryBranch = runePage.getSecondaryBranch();
		Rune keyRune = runePage.getKeyRune();
		// name validation
		if (StringUtils.hasLength(name)) {
			errors.rejectValue("name", "required", "required");
		}
//		
//		if (!StringUtils.hasLength(description)) {
//			errors.rejectValue("description", "required", "required");
//		}
//		
//		if (description.length() < 10) {
//			errors.rejectValue("description", "tooShort", "It is too short");
//		}
//		// type validation
//		if (item.isNew() && item.getType() == null) {
//			errors.rejectValue("type", REQUIRED, REQUIRED);
//		}

//		// birth date validation
//		if (pet.getBirthDate() == null) {
//			errors.rejectValue("birthDate", REQUIRED, REQUIRED);
//		}
//		
	}

}
