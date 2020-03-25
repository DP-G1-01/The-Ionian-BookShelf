package the_ionian_bookshelf.validators;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import the_ionian_bookshelf.model.Branch;
import the_ionian_bookshelf.model.Rune;

public class RuneValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Rune.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Rune rune = (Rune) target;	
		String name = rune.getName();
		String description = rune.getDescription();
		Branch branch = rune.getBranch();
		String node = rune.getNode();
		
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
		
		if(branch==null) {
			errors.rejectValue("branch", "isNull", "The branch must no be null");
		}
		
		if(!StringUtils.hasLength(node)) {
			errors.rejectValue("node", "isNull", "The node must no be null");
		}
	
	}

}