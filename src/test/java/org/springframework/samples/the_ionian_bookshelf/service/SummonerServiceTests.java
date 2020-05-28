package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.the_ionian_bookshelf.model.League;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.model.User;
import org.springframework.samples.the_ionian_bookshelf.utilities.AbstractTest;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class SummonerServiceTests extends AbstractTest{

	@Autowired
	protected SummonerService summonerService;
	
	@Autowired
	protected ThreadService threadService;
	
	@Autowired
	protected LeagueService leagueService;

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
	
	@Test
	@Transactional
	void testSaveSummoner() {
		this.authenticate("admin");
		Summoner res = new Summoner();
		res.setBanned(false);
		res.setEmail("summoner8@gmail.com");
		Thread thread = new Thread("Bronce league thread", "this is an example of thread", 0);
		this.threadService.save(thread);
		League league = new League("Bronzee", thread );
		
		this.leagueService.save(league);

		res.setLeague(league);
		res.setMains(null);
		User user = new User();
		user.setEnabled(true);
		user.setPassword("summoner8");
		user.setUsername("summoner8");
		res.setUser(user);
		
		this.summonerService.save(res);
		Summoner ii = this.summonerService.findOne(res.getId());
		assertEquals(res, ii);
	} 
	
	@Test
	@Transactional
	void testSaveNullError() {
		AssertionError exception = assertThrows(AssertionError.class, () -> this.summonerService.save(null));
		assertEquals(AssertionError.class, exception.getClass());
	}
}
