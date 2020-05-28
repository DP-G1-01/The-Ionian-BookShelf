package org.springframework.samples.the_ionian_bookshelf.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.the_ionian_bookshelf.model.Rune;
import org.springframework.samples.the_ionian_bookshelf.service.RuneService;

@ExtendWith(MockitoExtension.class)
class RuneFormatterTests {

	@Mock
	private RuneService runeService;
	
	private RuneFormatter runeFormatter;
	
	

	@BeforeEach
	void setup() {
		runeFormatter = new RuneFormatter(runeService);
	}

	@Test
	void testPrint() {
		Rune rune = new Rune();
		rune.setName("Water");
		String branchName = runeFormatter.print(rune, Locale.ENGLISH);
		assertEquals("Water", branchName);
	}

	@Test
	void shouldParse() throws ParseException {
		Mockito.when(runeService.findAll()).thenReturn(makeRunes());
		Rune rune = runeFormatter.parse("Water", Locale.ENGLISH);
		assertEquals("Water", rune.getName());
	}

	@Test
	void shouldThrowParseException() throws ParseException {
		Assertions.assertThrows(ParseException.class, () -> {
			runeFormatter.parse("Esto no lo parsea", Locale.ENGLISH);
		});
	}

	/*
	 * Produce Branches de ejemplo
	 */
	private Collection<Rune> makeRunes() {
		Collection<Rune> runes = new ArrayList<>();
		runes.add(new Rune() {
			{
				setName("Water");
			}
		});
		runes.add(new Rune() {
			{
				setName("Fire");
			}
		});
		return runes;
	}

}
