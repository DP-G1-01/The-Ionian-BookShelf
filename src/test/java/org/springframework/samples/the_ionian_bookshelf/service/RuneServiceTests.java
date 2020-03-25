package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.TheIonianBookshelfApplication;
import org.springframework.samples.the_ionian_bookshelf.model.Branch;
import org.springframework.samples.the_ionian_bookshelf.model.Rune;
import org.springframework.samples.the_ionian_bookshelf.repository.BranchRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.RunePageRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.RuneRepository;
import org.springframework.samples.the_ionian_bookshelf.service.RuneService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest (classes = TheIonianBookshelfApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
public class RuneServiceTests {

	@Autowired
	protected RuneService runeService;
	
	@Autowired
	protected BranchRepository branchRepository;
	
	@Autowired
	protected RuneRepository runeRepository;
	
	@Autowired
	protected RunePageRepository runePageRepository;
	
	@Test
	@BeforeAll
	void testFindAll() {
		Collection<Rune> runes = this.runeService.findAll();
		assertEquals(this.runeRepository.count(), runes.size());

	}
	
//	@Test
//	@AfterAll
//	void testFindAllEmpty() {
//		this.runePageRepository.deleteAll();
//		this.runeRepository.deleteAll();
//		assertEquals(0, this.runeRepository.count());
//	}
	
	@Test
	@Transactional
	void testSaveRune() {
		Branch branch = new Branch("Name", "Description", "http://www.image.com");
		this.branchRepository.save(branch);
		Rune rune = new Rune("Name", "Description", branch, "Key");
		this.runeService.saveRune(rune);
		//El id lo introduce automáticamente save, no he encontrado forma de ponerlo manualmente
		//Ni siquiera rune.setId(1000) funcionó
		Rune search = this.runeRepository.findById(36).get();
		assertEquals(rune, search);
	}
	@Test
	@Transactional
	void testFindBranches() {
		Collection<Branch> branches = runeService.findBranches();
		assertEquals(this.branchRepository.count(), branches.size());
	}
	
	@Test
	@Transactional
	void testDeleteRune() {
		long initial = this.runeRepository.count();
		Rune rune = this.runeRepository.findAll().get(0);
		this.runeService.deleteRune(rune);
		long after = this.runeRepository.count();
		assertEquals((initial-1), after);
	}
	
	@Test
	@Transactional
	void testDeleteInexistentRuneError() {
		AssertionError exception = assertThrows(AssertionError.class,()->this.runeService.deleteRune(null));
		assertEquals(AssertionError.class, exception.getClass());
	}

//	@Test
//	void shouldFindSingleRuneWithBranchAndNode() {
//		Rune rune = this.runeService.findRuneById(1);
//		assertThat(rune.getName()).startsWith("Prueba");
//		assertThat(rune.getDescription()!=null);
//		assertThat(rune.getBranch().getName().equals("NombreRama"));
//		assertThat(rune.getNode().equals("KEY"));
//	}
//
//	@Test
//	@Transactional
//	public void shouldInsertRune() {
//		Collection<Rune> runes = this.runeService.findRuneByName("NombreRuna");
//		int found = runes.size();
//
//		Branch branch1 = new Branch();
//		branch1.setName("SoyRama");
//		branch1.setDescription("Como rama hago tal");
//		branch1.setImage("http://www.miimagen.com");
//		this.branchService.saveBranch(branch1);
//		
//		Rune rune = new Rune();
//		rune.setName("SoyNombre");
//		rune.setDescription("Hago esto y esto");
//		rune.setBranch(branch1);
//		rune.setNode("KEY");
//		this.runeService.saveRune(rune);
//		assertThat(rune.getId().longValue()).isNotEqualTo(0);
//
//		runes = this.runeService.findRuneByName("NombreRuna");
//		assertThat(runes.size()).isEqualTo(found + 1);
//	}
//
//	@Test
//	@Transactional
//	void shouldUpdateOwner() {
//		Rune rune = this.runeService.findRuneById(1);
//		String oldLastName = rune.getName();
//		String newLastName = oldLastName + "X";
//
//		rune.setName(newLastName);
//		this.runeService.saveRune(rune);
//
//		// retrieving new name from database
//		rune = this.runeService.findRuneById(1);
//		assertThat(rune.getName()).isEqualTo(newLastName);
//	}
//
//	@SuppressWarnings("deprecation")
//	@Test
//	void shouldFindRuneWithCorrectIdAndBranch() {
//		Rune rune2 = this.runeService.findRuneById(2);
//		assertThat(rune2.getName()).startsWith("NombreRuna2");
//		assertThat(rune2.getBranch().getName()).isEqualTo("NombreBranch2");
//	}
//
//	@Test
//	void shouldFindAllBranches() {
//		Collection<Branch> branches = this.runeService.findBranches();
//
//		Branch branch1 = EntityUtils.getById(branches, Branch.class, 1);
//		assertThat(branch1.getName()).isEqualTo("NombreBranch1");
//		Branch branch3 = EntityUtils.getById(branches, Branch.class, 3);
//		assertThat(branch3.getName()).isEqualTo("NombreBranch3");
//	}
//
//
//	@Test
//	@Transactional
//	public void shouldUpdateBranchDescription() throws Exception {
//		Branch branch = this.branchService.findBranchById(1);
//		String oldDesc = branch.getDescription();
//
//		String newDesc = "Im a description!!";
//		branch.setDescription(newDesc);
//		this.branchService.saveBranch(branch);
//
//		branch = this.branchService.findBranchById(7);
//		assertThat(branch.getDescription()).isEqualTo(newDesc);
//	}


}
