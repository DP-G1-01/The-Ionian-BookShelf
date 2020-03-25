package org.springframework.samples.the_ionian_bookshelf.validators;

import org.springframework.samples.the_ionian_bookshelf.model.Branch;
import org.springframework.samples.the_ionian_bookshelf.model.Rune;
import org.springframework.samples.the_ionian_bookshelf.model.RunePage;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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
		Rune mainRune1 = runePage.getMainRune1();
		Rune mainRune2 = runePage.getMainRune2();
		Rune mainRune3 = runePage.getMainRune3();
		Rune secRune1 = runePage.getSecRune1();
		Rune secRune2 = runePage.getSecRune2();
		System.out.println(runePage.getName());
		System.out.println(runePage.getSummoner());
		System.out.println(runePage.getMainBranch());
		System.out.println(runePage.getSecondaryBranch());
		System.out.println(keyRune);
		System.out.println(runePage.getMainRune1());
		
		// name validation
		if (!StringUtils.hasLength(name)) {
			errors.rejectValue("name", "Name can not be empty", "Name can not be empty");
		}		
		else if (mainBranch == null) {
			errors.rejectValue("mainBranch", "A main branch must be selected", "A main branch must be selected");
		}
		
		else if (secondaryBranch == null) {
			errors.rejectValue("secondaryBranch", "A secondary branch must be selected", "A secondary branch must be selected");
		}
		
		else if(keyRune == null) {
			errors.rejectValue("keyRune", "A key rune must be selected", "A key rune must be selected");
		}
		
		else if(mainRune1 == null) {
			errors.rejectValue("mainRune1", "A first main rune must be selected", "A first main rune must be selected");
		}
		else if(mainRune2 == null) {
			errors.rejectValue("mainRune2", "A second main rune must be selected", "A second main rune must be selected");
		}
		else if(mainRune3 == null) {
			errors.rejectValue("mainRune3", "A third main rune must be selected", "A third main rune must be selected");
		}
		else if(secRune1 == null) {
			errors.rejectValue("secRune1", "A first secondary rune must be selected", "A first secondary rune must be selected");
		}
		else if(secRune2 == null) {
			errors.rejectValue("secRune2", "A second secondary rune must be selected", "A second secondary rune must be selected");
		}
		else if(summoner==null) {
			errors.rejectValue("summoner", "Summoner should not be null, are you logged in?", "Summoner should not be null, are you logged in?");
		}
		
		else if(keyRune.equals(mainRune1) || keyRune.equals(mainRune2) || keyRune.equals(mainRune3) || keyRune.equals(secRune1) || keyRune.equals(secRune2)
				 || mainRune1.equals(mainRune2) || mainRune1.equals(mainRune3) || mainRune1.equals(secRune1) || mainRune1.equals(secRune2)
				 || mainRune2.equals(mainRune3) || mainRune2.equals(secRune1) || mainRune2.equals(secRune2) || mainRune3.equals(secRune1)
				 || mainRune3.equals(secRune2) || secRune1.equals(secRune2)) {
			errors.rejectValue("keyRune", "All runes must be different each other", "All runes must be different each other");
		}
		
		else if(secRune1.getNode().equals("Key") || secRune2.getNode().equals("Key")) {
			errors.rejectValue("secRune1", "Secondary runes must not be from Key node", "Secondary runes must not be from Key node");
		}
		else if(!keyRune.getNode().equals("Key") || !mainRune1.getNode().equals("1") || !mainRune2.getNode().equals("2")|| !mainRune3.getNode().equals("3")) {
			errors.rejectValue("keyRune", "Main runes' nodes must coincide with their prefix", "Main runes' nodes must coincide with their prefix");
		}
		else if(secRune1.getNode().equals(secRune2.getNode())) {
			errors.rejectValue("secRune1", "Secondary runes must be from different nodes", "Secondary runes must be from different nodes");
		}
		else if(!keyRune.getBranch().equals(mainBranch)) {
			errors.rejectValue("keyRune", "Key rune must be from the selected main branch", "Key rune must be from the selected main branch");
		}
		else if(!mainRune1.getBranch().equals(mainBranch)) {
			errors.rejectValue("mainRune1", "First main rune must be from the selected main branch", "First main rune must be from the selected main branch");
		}else if(!mainRune2.getBranch().equals(mainBranch)) {
			errors.rejectValue("mainRune2", "Second main rune must be from the selected main branch", "Second main rune must be from the selected main branch");
		}else if(!mainRune3.getBranch().equals(mainBranch)) {
			errors.rejectValue("mainRune3", "Third main rune must be from the selected main branch", "Third main rune must be from the selected main branch");
		}else if(!secRune1.getBranch().equals(secondaryBranch)) {
			errors.rejectValue("secRune1", "First secondary rune must be from the selected secondary branch", "First secondary rune must be from the selected secondary branch");
		}else if(!secRune2.getBranch().equals(secondaryBranch)) {
			errors.rejectValue("secRune2", "Second secondary rune must be from the selected secondary branch", "Second secondary rune must be from the selected secondary branch");
		}
	}
}
