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
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.service.RuneService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;

@ExtendWith(MockitoExtension.class)
class SummonerFormatterTests {

	@Mock
	private SummonerService summonerService;

	@Mock
	private RuneService runeService;

	private SummonerFormatter summonerFormatter;

	@BeforeEach
	void setup() {
		summonerFormatter = new SummonerFormatter(summonerService);
	}

	@Test
	void testPrint() {
		Summoner summoner = new Summoner();
		summoner.setId(1);
		String summonerName = summonerFormatter.print(summoner, Locale.ENGLISH);
		assertEquals("1", summonerName);
	}

	@Test
	void shouldParse() throws ParseException {
		Mockito.when(summonerService.findAll()).thenReturn(makeSummoners());
		Summoner summoner = summonerFormatter.parse("1", Locale.ENGLISH);
		assertEquals(1, summoner.getId());
	}

	@Test
	void shouldThrowParseException() throws ParseException {
		Assertions.assertThrows(ParseException.class, () -> {
			summonerFormatter.parse("Esto no lo parsea", Locale.ENGLISH);
		});
	}

	/*
	 * Produce Branches de ejemplo
	 */
	private Collection<Summoner> makeSummoners() {
		Collection<Summoner> summoner = new ArrayList<>();
		summoner.add(new Summoner() {
			{
				setId(1);
			}
		});
		summoner.add(new Summoner() {
			{
				setId(2);
			}
		});
		return summoner;
	}

}
