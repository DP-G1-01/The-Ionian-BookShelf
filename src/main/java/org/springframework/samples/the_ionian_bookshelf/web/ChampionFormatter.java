//package org.springframework.samples.the_ionian_bookshelf.web;
//
//import java.text.ParseException;
//import java.util.Collection;
//import java.util.Locale;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.Formatter;
//import org.springframework.samples.the_ionian_bookshelf.model.Champion;
//import org.springframework.samples.the_ionian_bookshelf.service.ChampionService;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ChampionFormatter implements Formatter<Champion> {
//
////	@Autowired
////	private final ChampionService champService;
////
////	@Autowired
////	public ChampionFormatter(ChampionService champService) {
////		this.champService = champService;
////	}
////
////	@Override
////	public String print(Champion champ, Locale locale) {
////		return champ.getName();
////	}
////
////	@Override
////	public Champion parse(String text, Locale locale) throws ParseException {
////		Collection<Champion> findChamps = this.champService.findAll();
////		for (Champion champ : findChamps) {
////			if (champ.getId().toString().equals(text)) {
////				return champ;
////			}
////		}
////		throw new ParseException("type not found: " + text, 0);
////	}
//
//}
