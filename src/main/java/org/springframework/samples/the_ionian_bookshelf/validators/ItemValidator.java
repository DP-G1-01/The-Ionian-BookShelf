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
			errors.rejectValue("title", "required", "The title can't be empty.");
		}
		
		if (!StringUtils.hasLength(description)) {
			errors.rejectValue("description", "required", "The description can't be empty.");
		}
		
		if (title.length() > 40) {
			errors.rejectValue("title", "tooLong", "The title must have less than 40 characters.");
		}
		
		if (description.length() < 10) {
			errors.rejectValue("description", "tooShort", "The length must be superior to 10 characters.");
		}

		if (item.getAttributes().get(0).isEmpty()) {
			errors.rejectValue("attributes[0]", "emptyValue", "An attribute can't be empty.");
		}
		
		if (item.getAttributes().get(1).isEmpty()) {
			errors.rejectValue("attributes[1]", "emptyValue", "An attribute can't be empty.");
		}
		
		if (item.getAttributes().get(2).isEmpty()) {
			errors.rejectValue("attributes[2]", "emptyValue", "An attribute can't be empty.");
		}
		
		if(item.getRoles() == null) {
			errors.rejectValue("roles[0]", "nullRol", "A rol must be selected.");
		}
	}

}
