package the_ionian_bookshelf.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import the_ionian_bookshelf.model.Branch;
import the_ionian_bookshelf.service.RuneService;

@Component
public class BranchFormatter implements Formatter<Branch> {

	private final RuneService runeService;

	@Autowired
	public BranchFormatter(RuneService runeService) {
		this.runeService = runeService;
	}

	@Override
	public String print(Branch branch, Locale locale) {
		return branch.getName();
	}

	@Override
	public Branch parse(String text, Locale locale) throws ParseException {
		Collection<Branch> findBranches = this.runeService.findBranches();
		for (Branch branch : findBranches) {
			if (branch.getName().equals(text)) {
				return branch;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
