package org.springframework.samples.theionianbookshelf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.theionianbookshelf.repository.ChampionRepository;
import org.springframework.stereotype.Service;

@Service
public class ChampionService {
	
	@Autowired
	private ChampionRepository champRepo;
	
	

}
