package org.springframework.samples.the_ionian_bookshelf.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.the_ionian_bookshelf.model.Rune;
import org.springframework.samples.the_ionian_bookshelf.service.RuneService;
import org.springframework.stereotype.Component;

@Component
public class RuneFormatter implements Formatter<Rune> {

	private final RuneService runeService;

	@Autowired
	public RuneFormatter(RuneService runeService) {
		this.runeService = runeService;
	}

	@Override
	public String print(Rune rune, Locale locale) {
		return rune.getName();
	}

	@Override
	public Rune parse(String text, Locale locale) throws ParseException {
		Collection<Rune> findRunes = this.runeService.findAll();
		for (Rune rune : findRunes) {
			if (rune.getName().equals(text)) {
				return rune;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
