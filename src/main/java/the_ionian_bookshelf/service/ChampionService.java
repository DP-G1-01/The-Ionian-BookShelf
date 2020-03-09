package the_ionian_bookshelf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import the_ionian_bookshelf.repository.ChampionRepository;

@Service
public class ChampionService {

	@Autowired
	private ChampionRepository champRepo;

}
