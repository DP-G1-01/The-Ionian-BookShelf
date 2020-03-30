package org.springframework.samples.the_ionian_bookshelf.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.stereotype.Component;

@Component
public class SummonerFormatter implements Formatter<Summoner> {

	private final SummonerService summonerService;

	@Autowired
	public SummonerFormatter(SummonerService summonerService) {
		this.summonerService = summonerService;
	}

	@Override
	public String print(Summoner summoner, Locale locale) {
		return summoner.getId().toString();
	}

	@Override
	public Summoner parse(String text, Locale locale) throws ParseException {
		Collection<Summoner> findSummoners = this.summonerService.findAll();
		for (Summoner summoner : findSummoners) {
			if (summoner.getId().toString().equals(text)) {
				return summoner;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
