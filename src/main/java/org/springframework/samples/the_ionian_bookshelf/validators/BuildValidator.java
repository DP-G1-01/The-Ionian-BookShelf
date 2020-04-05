package org.springframework.samples.the_ionian_bookshelf.validators;

import org.springframework.samples.the_ionian_bookshelf.model.Build;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BuildValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Build.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Build build = (Build) target;
		String title = build.getTitle();
		String description = build.getDescription();
		// name validation
		if (!StringUtils.hasLength(title)) {
			errors.rejectValue("title", "required", "required");
		}

		if (!StringUtils.hasLength(description)) {
			errors.rejectValue("description", "required", "required");
		}

		if (description.length() < 20) {
			errors.rejectValue("description", "tooShort", "It is too short");
		}
	}

}