package org.springframework.samples.theionianbookshelf.web;

import static org.junit.Assert.assertEquals;

import java.security.Provider.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import the_ionian_bookshelf.model.Branch;
import the_ionian_bookshelf.model.Rune;
import the_ionian_bookshelf.service.RuneService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class RuneServiceTest {

	@Autowired
	private RuneService runeService;
	
	@Test
	public void testSaveRune() {
		Branch branch = new Branch("Name", "Description", "http://www.image.com");
		branch.setId(100);
		Rune rune = new Rune("Name", "Description", branch, "Key");
		rune.setId(100);
		this.runeService.saveRune(rune);
		Rune search = this.runeService.findRuneById(100);
		assertEquals(rune, search);
	}
}
