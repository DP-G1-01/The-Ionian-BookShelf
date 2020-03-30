package org.springframework.samples.the_ionian_bookshelf.validators;

import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ItemValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Item.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Item item = (Item) target;
		String title = item.getTitle();
		String description = item.getDescription();
		// name validation
		if (!StringUtils.hasLength(title)) {
			errors.rejectValue("title", "required", "required");
		}
		
		if (!StringUtils.hasLength(description)) {
			errors.rejectValue("description", "required", "required");
		}
		
		if (description.length() < 10) {
			errors.rejectValue("description", "tooShort", "It is too short");
		}
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
