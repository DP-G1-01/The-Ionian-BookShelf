package org.springframework.samples.the_ionian_bookshelf.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.model.Branch;
import org.springframework.samples.the_ionian_bookshelf.model.Rune;
import org.springframework.samples.the_ionian_bookshelf.repository.BranchRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.RunePageRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.RuneRepository;
import org.springframework.samples.the_ionian_bookshelf.service.RuneService;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
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

	// Encontrar una runa por su id
	@Test
	@Transactional
	void testFindRuneById() {
		Rune i = runeService.findRuneById(1);
		Rune ii = runeRepository.findById(1).get();
		assertEquals(i, ii);
	}

	@Test
	@Transactional
	void testFindRuneByIdError() {
		NoSuchElementException exception = assertThrows(NoSuchElementException.class,
				() -> runeService.findRuneById(3472));
		assertEquals(NoSuchElementException.class, exception.getClass());
	}

	
	//Guardar una nueva runa
	@WithMockUser(value = "admin")
	@Test
	@Transactional
	void testSaveRune() {
		Branch branch = new Branch("Name", "Description", "http://www.image.com");
		this.branchRepository.save(branch);
		Rune rune = new Rune("Name", "Description", branch, "Key");
		this.runeService.saveRune(rune);
		Rune search = this.runeRepository.findById(rune.getId()).get();
		assertEquals(rune, search);
	}

	//Eliminar una runa existente
	@WithMockUser(value = "admin")
	@Test
	@Transactional
	void testDeleteRune() {
		long initial = this.runeRepository.count();
		Rune rune = this.runeRepository.findAll().get(0);
		this.runeService.deleteRune(rune);
		long after = this.runeRepository.count();
		assertEquals((initial - 1), after);
	}

	@Test
	@Transactional
	void testDeleteInexistentRune() {
		AssertionError exception = assertThrows(AssertionError.class, () -> this.runeService.deleteRune(null));
		assertEquals(AssertionError.class, exception.getClass());
	}
	
	//Update Runa
	@WithMockUser(value = "admin")
	@Test
	@Transactional
	public void shouldUpdateRuneDescription() throws Exception {
		Rune rune= this.runeService.findRuneById(1);
		String oldDesc = rune.getDescription();

		String newDesc = "Im a description!!";
		rune.setDescription(newDesc);
		this.runeService.saveRune(rune);
		rune = this.runeService.findRuneById(rune.getId());
		assertThat(rune.getDescription()).isEqualTo(newDesc);
	}
	
	
	@Test
	@Transactional
	void testFindBranches() {
		Collection<Branch> branches = runeService.findBranches();
		assertEquals(this.branchRepository.count(), branches.size());
	}


}
