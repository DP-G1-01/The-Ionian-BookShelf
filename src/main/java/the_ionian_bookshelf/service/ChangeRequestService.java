package the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import the_ionian_bookshelf.model.Actor;
import the_ionian_bookshelf.model.Authority;
import the_ionian_bookshelf.model.ChangeRequest;
import the_ionian_bookshelf.model.Summoner;
import the_ionian_bookshelf.repository.ChangeRequestRepository;

@Service
public class ChangeRequestService {

	@Autowired
	private ChangeRequestRepository changeRepository;
	
	@Autowired
	private AdministratorService adminService;

	@Autowired
	private ReviewerService reviewerService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private SummonerService summonerService;


	public ChangeRequest create() {

		Summoner summoner = this.summonerService.findByPrincipal();

		//Champion defaultChamp = this.changeionService.create();
		
		ChangeRequest res = new ChangeRequest();
		res.setTitle("New title");
		res.setDescription("New description");
		res.setChampion(null);
		res.setItem(null);
		res.setChangeChamp(null);
		res.setChangeItem(null);
		res.setReviewer(null);
		res.setStatus("PENDING");
		res.setSummoner(summoner);

		return res;
	}

	public Collection<ChangeRequest> findAll() {

		Collection<ChangeRequest> res = this.changeRepository.findAll();
		assertNotNull(res);

		return res;
	}

	public ChangeRequest findOne(int id) {

		assertTrue(id != 0);

		final ChangeRequest res = this.changeRepository.findById(id).get();
		assertNotNull(res);

		return res;

	}

	public ChangeRequest save(ChangeRequest change) {

		assertNotNull(change);

		Actor principal = this.actorService.findByPrincipal();

		assertTrue(this.actorService.checkAuthority(principal, Authority.SUMMONER));

		return this.changeRepository.save(change);
	}
	
	public ChangeRequest resolve(ChangeRequest change) {

		assertNotNull(change);

		Actor principal = this.actorService.findByPrincipal();

		assertTrue(this.actorService.checkAuthority(principal, Authority.REVIEWER));

		return this.changeRepository.save(change);
	}

	public void delete(ChangeRequest change) {

		assertNotNull(change);
		this.reviewerService.findByPrincipal();

	}

}
