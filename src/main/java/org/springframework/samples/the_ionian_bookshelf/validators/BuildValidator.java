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
			errors.rejectValue("title", "required", "The title can't be empty.");
        }

        if (!StringUtils.hasLength(description)) {
			errors.rejectValue("description", "required", "The description can't be empty.");
        }
        
        if (title.length() > 40) {
			errors.rejectValue("title", "tooLong", "The title must have less than 40 characters.");
		}

        if (description.length() < 20) {
			errors.rejectValue("description", "tooShort", "The length must be superior to 20 characters.");
        }
        
        if(build.getItems() == null || build.getItems().contains(null)) {
			errors.rejectValue("items[0]", "nullItem", "An item must be selected.");
		}
    }

}
