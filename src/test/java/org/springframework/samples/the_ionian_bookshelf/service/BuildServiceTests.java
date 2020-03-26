package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.model.Build;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.ChangeRequest;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.repository.BuildRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.ChampionRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.ItemRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.RoleRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class BuildServiceTests {
	
	@Autowired
	private BuildService buildService;

	@Autowired
	protected BuildRepository buildRepository;

	@Autowired
	protected ChampionRepository championRepository;
	
	@Autowired
	protected ItemRepository itemRepository;
	
	@Autowired
	protected RoleRepository roleRepository;

	
	@Test
	@BeforeAll
	void testFindAll() {
		Collection<Build> builds = buildService.findAll();
		assertEquals(buildRepository.count(), builds.size());
	}
	
	@Test
	@Transactional
	@AfterAll
	void testFindAllEmpty() {
		buildRepository.deleteAll();
		assertEquals(0, buildRepository.count());
	}
	
	@Test
	@Transactional
	void testFindOne() {
		Build build = buildService.findBuildById(1);
		Build build2 = buildRepository.findBuildById(1);
		assertEquals(build, build2);
	}
	
	@Test
	@Transactional
	void testFindOneError() {
		AssertionError exception = assertThrows(AssertionError.class,()->buildService.findBuildById(3472));
		assertEquals(AssertionError.class, exception.getClass());
	}
	
	@Test
	@Transactional
	void testRemoveBuildById() {
		long l = buildRepository.count();
		buildService.removeBuildById(1);
		long l2 = buildRepository.count();
		assertEquals((l-1), l2);
	}
	
	@Test
	@Transactional
	void testRemoveBuildByIdError() {
		AssertionError exception = assertThrows(AssertionError.class,()->buildService.removeBuildById(4637));
		assertEquals(AssertionError.class, exception.getClass());
	}
	
	@Test
	@Transactional
	void testSaveBuild() {
	Role r = new Role("Rol1", "Soy un rol de prueba ten paciencia", "https://www.youtube.com/");
	roleRepository.save(r);
	Champion c = new Champion("Cham1", "La descripción es algo superfluo sin cabida en una mente abierta",
			10., 5., null, 20., 50., r);
	championRepository.save(c);
	Build build = new Build("Build de testeo", "Soy una build con una descripción muy bonita, sí", false, new ArrayList<>(), c, null, null);
	buildService.saveBuild(build);
	Build build2 = buildService.findBuildById(build.getId());
	assertEquals(build, build2);
	}
}
