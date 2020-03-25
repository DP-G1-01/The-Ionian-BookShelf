package org.springframework.samples.the_ionian_bookshelf.validators;

import org.springframework.samples.the_ionian_bookshelf.model.ChangeRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ChangeRequestValidator  implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return ChangeRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ChangeRequest request = (ChangeRequest) target;
		String title = request.getTitle();
		String description = request.getDescription();
		String status = request.getStatus();
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
		
		if (status != "PENDING" || status != "ACCEPTED" || status != "REJECTED") {
			errors.rejectValue("status", "incorrectPattern", "The status doesn't fit the pattern");
		}
		
//		// type validation
//		if (request.isNew() && request.getType() == null) {
//			errors.rejectValue("type", REQUIRED, REQUIRED);
//		}

//		// birth date validation
//		if (pet.getBirthDate() == null) {
//			errors.rejectValue("birthDate", REQUIRED, REQUIRED);
//		}
//		
	}

}
