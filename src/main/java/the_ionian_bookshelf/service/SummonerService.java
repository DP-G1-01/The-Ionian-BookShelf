package the_ionian_bookshelf.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import the_ionian_bookshelf.model.Summoner;
import the_ionian_bookshelf.repository.SummonerRepository;

@Service
public class SummonerService {

	@Autowired
	private SummonerRepository summonerRepo;
	
	@Transactional
	public Summoner findOneSummonerById(int id) {
		return this.summonerRepo.getOne(id);
	}

	
}
