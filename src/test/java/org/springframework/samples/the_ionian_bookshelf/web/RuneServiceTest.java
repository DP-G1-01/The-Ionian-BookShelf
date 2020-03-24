package org.springframework.samples.the_ionian_bookshelf.web;

import static org.junit.Assert.assertEquals;

import java.security.Provider.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.the_ionian_bookshelf.TheIonianBookshelfApplication;
import org.springframework.samples.the_ionian_bookshelf.model.Branch;
import org.springframework.samples.the_ionian_bookshelf.model.Rune;
import org.springframework.samples.the_ionian_bookshelf.service.RuneService;

@SpringBootTest
public class RuneServiceTest {

	@Autowired
	private RuneService runeService;
	
//	@Test
//	public void testSaveRune() {
//		Branch branch = new Branch("Name", "Description", "http://www.image.com");
//		branch.setId(100);
//		Rune rune = new Rune("Name", "Description", branch, "Key");
//		rune.setId(100);
//		this.runeService.saveRune(rune);
//		Rune search = this.runeService.findRuneById(100);
//		assertEquals(rune, search);
//	}
}
