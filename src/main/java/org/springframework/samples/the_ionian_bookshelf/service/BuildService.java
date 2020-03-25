package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Build;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.repository.BuildRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BuildService {

	@Autowired
	private BuildRepository buildRepository;

	public Collection<Build> findByChamp(Champion champ) {

		assertNotNull(champ);
		assertTrue(champ.getId() != 0);

		Collection<Build> res = this.buildRepository.findByChampion(champ);
		assertNotNull(res);

		return res;

	}

}
