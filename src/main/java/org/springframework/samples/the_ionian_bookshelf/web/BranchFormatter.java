package org.springframework.samples.the_ionian_bookshelf.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.the_ionian_bookshelf.model.Branch;
import org.springframework.samples.the_ionian_bookshelf.service.BranchService;
import org.springframework.stereotype.Component;

@Component
public class BranchFormatter implements Formatter<Branch> {

	@Autowired
	private final BranchService branchService;

	@Autowired
	public BranchFormatter(BranchService branchService) {
		this.branchService = branchService;
	}

	@Override
	public String print(Branch branch, Locale locale) {
		return branch.getName();
	}

	@Override
	public Branch parse(String text, Locale locale) throws ParseException {
		Collection<Branch> findBranches = this.branchService.findAll();
		for (Branch branch : findBranches) {
			if (branch.getName().equals(text)) {
				return branch;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
