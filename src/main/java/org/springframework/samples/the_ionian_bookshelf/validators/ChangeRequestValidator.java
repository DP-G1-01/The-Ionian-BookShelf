package org.springframework.samples.the_ionian_bookshelf.validators;

import java.util.List;

import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.ChangeRequest;
import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ChangeRequestValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ChangeRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		System.out.println("validate");
		ChangeRequest request = (ChangeRequest) target;
		String title = request.getTitle();
		String description = request.getDescription();
		Champion campeon = request.getChampion();
		Item item = request.getItem();
		// name validation
		System.out.println("Muestrame: " + title);
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

		if (campeon != null) {
			List<String> changes = request.getChangeChamp();

			if (changes.get(0).isEmpty()) {
				errors.rejectValue("changeChamp[0]", "emptyValue", "An attribute can't be empty.");
			} else if (Math.abs(campeon.getHealth() - Double.parseDouble(changes.get(0))) >= 10) {
				errors.rejectValue("changeChamp[0]", "exagerateValue", "The difference with the original attribute is superior to 10.");
			}

			if (changes.get(1).isEmpty()) {
				errors.rejectValue("changeChamp[1]", "emptyValue", "An attribute can't be empty.");
			} else if (campeon.getMana() != null
					&& Math.abs(campeon.getMana() - Double.parseDouble(changes.get(1))) >= 10) {
				errors.rejectValue("changeChamp[1]", "exagerateValue", "The difference with the original attribute is superior to 10.");
			} else if (campeon.getEnergy() != null
					&& Math.abs(campeon.getEnergy() - Double.parseDouble(changes.get(1))) >= 10) {
				errors.rejectValue("changeChamp[1]", "exagerateValue", "The difference with the original attribute is superior to 10.");
			}

			if (changes.get(2).isEmpty()) {
				errors.rejectValue("changeChamp[2]", "emptyValue", "An attribute can't be empty.");
			} else if (Math.abs(campeon.getAttack() - Double.parseDouble(changes.get(2))) >= 10) {
				errors.rejectValue("changeChamp[2]", "exagerateValue", "The difference with the original attribute is superior to 10.");
			}

			if (changes.get(3).isEmpty()) {
				errors.rejectValue("changeChamp[3]", "emptyValue", "An attribute can't be empty.");
			} else if (Math.abs(campeon.getSpeed() - Double.parseDouble(changes.get(3))) >= 10) {
				errors.rejectValue("changeChamp[3]", "exagerateValue", "The difference with the original attribute is superior to 10.");
			}

		} else if (item != null) {
			for (int i = 0; i < request.getChangeItem().size(); i++) {
				if (request.getChangeItem().get(i).isEmpty()) {
					errors.rejectValue("changeItem[" + i + "]", "emptyValue", "An attribute can't be empty.");
				} else {
					Integer valorNuevo = Integer.parseInt(request.getChangeItem().get(i));
					Integer valorViejo = Integer.parseInt(request.getItem().getAttributes().get(i));

					if (Math.abs(valorNuevo - valorViejo) >= 10) {
						errors.rejectValue("changeItem[" + i + "]", "exagerateValue",
								"The difference with the original attribute is superior to 10.");
					}
				}
			}
		}
	}

}
