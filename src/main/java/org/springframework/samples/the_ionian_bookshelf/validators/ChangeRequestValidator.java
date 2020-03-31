package org.springframework.samples.the_ionian_bookshelf.validators;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.ChangeRequest;
import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ChangeRequestValidator implements Validator{

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
		Champion campeon = request.getChampion();
		Item item = request.getItem();
		// name validation
		System.out.println("Muestrame: " + title);
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
		
		if(campeon != null) {
			List<Double> nuevosValores = request.getChangeChamp().stream().map(x -> Double.parseDouble(x))
					.collect(Collectors.toList());

			if (campeon.getHealth().compareTo(nuevosValores.get(0)) >= 10) {
				errors.rejectValue("changeChamp", "exagerateValue", "the difference is superior to 10");
			} else if (campeon.getMana() != null && campeon.getMana().compareTo(nuevosValores.get(1)) >= 10) {
				errors.rejectValue("changeChamp", "exagerateValue", "the difference is superior to 10");
			} else if (campeon.getEnergy() != null && campeon.getEnergy().compareTo(nuevosValores.get(2)) >= 10) {
				errors.rejectValue("changeChamp", "exagerateValue", "the difference is superior to 10");
			} else if (campeon.getAttack().compareTo(nuevosValores.get(3)) >= 10) {
				errors.rejectValue("changeChamp", "exagerateValue", "the difference is superior to 10");
			} else if (campeon.getSpeed().compareTo(nuevosValores.get(4)) >= 10) {
				errors.rejectValue("changeChamp", "exagerateValue", "the difference is superior to 10");
			} else if (request.getChangeChamp().get(1) != null && request.getChangeChamp().get(2) != null) {
				errors.rejectValue("changeChamp", "ManaAndEnergy", "only mana or energy, not both");
			}
		} else if(item != null) {
			for (int i = 0; i < request.getChangeItem().size(); i++) {
				Integer valorNuevo = Integer.parseInt(request.getChangeItem().get(i));
				Integer valorViejo = Integer.parseInt(request.getItem().getAttributes().get(i));

				if (valorNuevo.compareTo(valorViejo) >= 10) {
					errors.rejectValue("changeItem", "exagerateValue", "the difference is superior to 10");
				}
			}
		}
	}

}
