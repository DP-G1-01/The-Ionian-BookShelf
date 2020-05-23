package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class SummonerServiceTests {

	@Autowired
	protected SummonerService summonerService;
	
	@Test
	@Transactional
	void testBan() {
		Summoner summoner = this.summonerService.findOne(1);
		this.summonerService.banear(summoner);
		assertEquals(summoner.getBanned(), true);
	}
	
	@Test
	@Transactional
	void testDesban() {
		Summoner summoner = this.summonerService.findOne(1);
		this.summonerService.desbanear(summoner);
		assertEquals(summoner.getBanned(), false);
	}
}
