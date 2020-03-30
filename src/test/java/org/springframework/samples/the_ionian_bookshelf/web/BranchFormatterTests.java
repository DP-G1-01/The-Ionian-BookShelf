package org.springframework.samples.the_ionian_bookshelf.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.the_ionian_bookshelf.model.Branch;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.service.BranchService;
import org.springframework.samples.the_ionian_bookshelf.service.RoleService;
import org.springframework.samples.the_ionian_bookshelf.service.RuneService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BranchFormatterTests {

	@Mock
	private BranchService branchService;
	
	@Mock
	private RuneService runeService;

	private BranchFormatter branchFormatter;
	
	

	@BeforeEach
	void setup() {
		branchFormatter = new BranchFormatter(branchService);
	}

	@Test
	void testPrint() {
		Branch branch = new Branch();
		branch.setName("Thunder");
		String branchName = branchFormatter.print(branch, Locale.ENGLISH);
		assertEquals("Thunder", branchName);
	}

	@Test
	void shouldParse() throws ParseException {
		Mockito.when(branchService.findAll()).thenReturn(makeRoles());
		Branch branch = branchFormatter.parse("Thunder", Locale.ENGLISH);
		assertEquals("Thunder", branch.getName());
	}

	@Test
	void shouldThrowParseException() throws ParseException {
		Assertions.assertThrows(ParseException.class, () -> {
			branchFormatter.parse("Esto no lo parsea", Locale.ENGLISH);
		});
	}

	/*
	 * Produce Branches de ejemplo
	 */
	private Collection<Branch> makeRoles() {
		Collection<Branch> branch = new ArrayList<>();
		branch.add(new Branch() {
			{
				setName("Thunder");
			}
		});
		branch.add(new Branch() {
			{
				setName("Sun");
			}
		});
		return branch;
	}

}
