package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.model.Branch;
import org.springframework.samples.the_ionian_bookshelf.model.Rune;
import org.springframework.samples.the_ionian_bookshelf.model.RunePage;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.repository.BranchRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.RunePageRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.RuneRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.SummonerRepository;
import org.springframework.samples.the_ionian_bookshelf.utilities.AbstractTest;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class RunePageServiceTests extends AbstractTest {

	@Mock
	protected BranchService branchService;
	
	@Autowired
	protected BranchRepository branchRepository;

	@Autowired
	protected RunePageRepository runePageRepository;

	@Autowired
	private RunePageService runePageService;
	
	@Autowired
	private RuneRepository runeRepository;

	@Autowired
	private SummonerRepository summonerRepository;

	@Test
	@Transactional
	void testFindAllMine() {
		this.authenticate("summoner1");
		Summoner summoner = this.summonerRepository.findByUsername("summoner1");
		Collection<RunePage> runePages = runePageService.findAllMine();
		for (RunePage runePage : runePages) {
			assertEquals(summoner, runePage.getSummoner());
		}
	}

	@Test
	@Transactional
	void testFindAllMineError() {
		this.authenticate(null);
		AssertionError exception = assertThrows(AssertionError.class, () -> this.runePageService.findAllMine());
		assertEquals(AssertionError.class, exception.getClass());
	}

//	@Test
//	@Transactional
//	void testFindOne() {
//		RunePage i = this.runePageService.findOne(1);
//		RunePage ii = this.runePageRepository.findById(1).get();
//		assertEquals(i, ii);
//	}

	@Test
	@Transactional
	void testFindOneError() {
		// El método de Optional<T> .get() devuelve NoSuchElementException si no
		// encuentra objeto, no devuelve null
		NoSuchElementException exception = assertThrows(NoSuchElementException.class,
				() -> this.runePageService.findOne(10000));
		assertEquals(NoSuchElementException.class, exception.getClass());
	}

	@Test
	@Transactional
	void testDelete() {
		long l = this.runePageRepository.count();
		RunePage runePage = this.runePageRepository.findAll().get(0);
		this.authenticate(runePage.getSummoner().getUser().getUsername());
		this.runePageService.delete(runePage);
		long l2 = this.runePageRepository.count();
		assertEquals((l - 1), l2);
	}

	@Test
	@Transactional
	void testDeleteNullError() {
		AssertionError exception = assertThrows(AssertionError.class, () -> runePageService.delete(null));
		assertEquals(AssertionError.class, exception.getClass());
	}

	@Test
	@Transactional
	void testDeleteNoIdError() {
		RunePage runePage = new RunePage();
		AssertionError exception = assertThrows(AssertionError.class, () -> runePageService.delete(runePage));
		assertEquals(AssertionError.class, exception.getClass());
	}

	@Test
	@Transactional
	void testDeleteNotYoursError() {
		RunePage runePage = this.runePageRepository.findAll().get(0);
		this.authenticate("summoner3");
		AssertionError exception = assertThrows(AssertionError.class, () -> runePageService.delete(runePage));
		assertEquals(AssertionError.class, exception.getClass());
	}

	@Test
	@Transactional
	void testFindBranches() {
		Collection<Branch> branches = this.runePageService.findBranches();
		assertEquals(this.branchRepository.count(), branches.size());
	}

	@Test
	@Transactional
	void testFindRunesByBranchNode() {
		List<List<Rune>> runesByBranchNode = this.runePageService.findRunesByBranchNode();
		for (List<Rune> runes : runesByBranchNode) {
			Branch runesBranch = runes.get(0).getBranch();
			String runesNode = runes.get(0).getNode();
			for (Rune rune : runes) {
				assertEquals(runesBranch, rune.getBranch());
				assertEquals(runesNode, rune.getNode());
			}
		}
	}

	@Test
	@Transactional
	void testFindSecondaryRunesByBranch() {
		List<List<Rune>> runesByBranchNode = this.runePageService.findSecondaryRunesByBranch();
		for (List<Rune> runes : runesByBranchNode) {
			Branch runesBranch = runes.get(0).getBranch();
			for (Rune rune : runes) {
				assertEquals(true, !rune.getNode().equals("Key"));
				assertEquals(runesBranch, rune.getBranch());
			}
		}
	}
	
	@Test
	@Transactional
	void testSaveNewRune() {
		this.authenticate("summoner1");
		Branch mainBranch = new Branch("Test", "description", "http://www.image.com");
		Branch secondaryBranch = new Branch("Test", "description", "http://www.image.com");
		this.branchRepository.save(mainBranch);
		this.branchRepository.save(secondaryBranch);
		Rune keyRune = new Rune("Test Key Rune", "description", mainBranch, "Key");
		Rune mainRune1 = new Rune("Test Main Rune 1", "description", mainBranch, "1");
		Rune mainRune2 = new Rune("Test Main Rune 2", "description", mainBranch, "2");
		Rune mainRune3 = new Rune("Test Main Rune 3", "description", mainBranch, "3");
		Rune secRune1 = new Rune("Test Secondary Rune 1", "description", secondaryBranch, "1");
		Rune secRune2 = new Rune("Test Secondary Rune 2", "description", secondaryBranch, "2");
		this.runeRepository.save(keyRune);
		this.runeRepository.save(mainRune1);
		this.runeRepository.save(mainRune2);
		this.runeRepository.save(mainRune3);
		this.runeRepository.save(secRune1);
		this.runeRepository.save(secRune2);
		RunePage runePage = new RunePage("New Rune Page", this.summonerRepository.findByUsername("summoner1"), mainBranch, secondaryBranch, keyRune, mainRune1, mainRune2, mainRune3, secRune1, secRune2);
		this.runePageService.save(runePage);
		RunePage ii = this.runePageRepository.findById(runePage.getId()).get();
		assertEquals(runePage, ii);
	} 
	@Test
	@Transactional
	void testSaveNullError() {
		this.authenticate("summoner1");
		AssertionError exception = assertThrows(AssertionError.class, () -> runePageService.save(null));
		assertEquals(AssertionError.class, exception.getClass());
	}
	
	@Test
	@Transactional
	void testSaveNotYoursError() {
		this.authenticate("summoner3");
		RunePage runePage = this.runePageRepository.findById(1).get();
		AssertionError exception = assertThrows(AssertionError.class, () -> runePageService.save(runePage));
		assertEquals(AssertionError.class, exception.getClass());
	}
}
