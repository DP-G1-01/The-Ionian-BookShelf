package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Build;
import org.springframework.samples.the_ionian_bookshelf.model.Message;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.model.Vote;
import org.springframework.samples.the_ionian_bookshelf.repository.VoteRepository;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

	@Autowired
	private VoteRepository voteRepo;
	@Autowired
	private ThreadService threadService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private BuildService buildService;
	@Autowired
	private SummonerService summonerService;

	public Collection<Vote> findByThread(Thread thread) {

		assertNotNull(thread);
		assertTrue(thread.getId() != 0);

		Collection<Vote> res = this.voteRepo.findByThread(thread.getId());
		assertNotNull(res);

		return res;
	}

	public void createUpVoteByThreadId(int id) {
		assertTrue(id != 0);
		Thread thread = threadService.findOne(id);
		assertNotNull(thread);
		Summoner voter = this.summonerService.findByPrincipal();
		assertNotNull(voter);
		Collection<Vote> votes = voteRepo.findByThread(id);
		assertNotNull(votes);
		for (Vote v : votes) {
			if (v.getVoter().getId().equals(voter.getId())) {
				if (v.isStatus() == false) {
					delete(v);
				} else {
					assertNotEquals("HAS VUELTO A VOTAR LO MISMO", v.isStatus(), true);
				}
			}
		}
		Vote vote = new Vote(voter, null, thread, null, true);
		save(vote);
	}

	public void createDownVoteByThreadId(int id) {
		assertTrue(id != 0);
		Thread thread = threadService.findOne(id);
		assertNotNull(thread);
		Summoner voter = this.summonerService.findByPrincipal();
		assertNotNull(voter);
		Collection<Vote> votes = voteRepo.findByThread(id);
		assertNotNull(votes);
		for (Vote v : votes) {
			if (v.getVoter().getId().equals(voter.getId())) {
				if (v.isStatus() == true) {
					delete(v);
				} else {
					assertNotEquals("HAS VUELTO A VOTAR LO MISMO", v.isStatus(), false);
				}
			}
		}
		Vote vote = new Vote(voter, null, thread, null, false);
		save(vote);
	}

	public void deleteByThreadId(int id) {
		assertTrue(id != 0);
		Collection<Vote> votes = this.voteRepo.findByThread(id);
		for (Vote vote : votes) {
			this.delete(vote);
		}
	}

	public Integer getPuntuationThread(Thread thread) {
		assertNotNull(thread);
		assertTrue(thread.getId() != 0);
		Integer positives = this.voteRepo.countPositivesVotesByThread(thread.getId());
		Integer negatives = this.voteRepo.countNegativesVotesByThread(thread.getId());

		return positives - negatives;

	}

	public Collection<Vote> findByMessageId(int id) {
		assertTrue(id != 0);
		Collection<Vote> res = this.voteRepo.findByMessageId(id);
		assertNotNull(res);
		return res;
	}

	public Integer getPuntuationMessage(Message message) {
		assertNotNull(message);
		assertTrue(message.getId() != 0);
		Integer positives = this.voteRepo.countPositivesVotesByMessage(message.getId());
		Integer negatives = this.voteRepo.countNegativesVotesByMessage(message.getId());

		return positives - negatives;

	}

	public void createUpVoteByMessageId(int messageId) {
		assertTrue(messageId != 0);
		Message message = messageService.findOneMesageById(messageId);
		assertNotNull(message);
		Summoner voter = this.summonerService.findByPrincipal();
		assertNotNull(voter);
		Collection<Vote> votes = voteRepo.findByMessageId(messageId);
		assertNotNull(votes);
		for (Vote v : votes) {
			if (v.getVoter().getId().equals(voter.getId())) {
				if (v.isStatus() == false) {
					delete(v);
				} else {
					assertNotEquals("HAS VUELTO A VOTAR LO MISMO", v.isStatus(), true);
				}
			}
		}
		Vote vote = new Vote(voter, null, null, message, true);
		save(vote);
	}

	public void createDownVoteByMessageId(int messageId) {
		assertTrue(messageId != 0);
		Message message = messageService.findOneMesageById(messageId);
		assertNotNull(message);
		Summoner voter = this.summonerService.findByPrincipal();
		assertNotNull(voter);
		Collection<Vote> votes = voteRepo.findByMessageId(messageId);
		assertNotNull(votes);
		for (Vote v : votes) {
			if (v.getVoter().getId().equals(voter.getId())) {
				if (v.isStatus() == true) {
					delete(v);
				} else {
					assertNotEquals("HAS VUELTO A VOTAR LO MISMO", v.isStatus(), false);
				}
			}
		}
		Vote vote = new Vote(voter, null, null, message, false);
		save(vote);
	}

	public void deleteByMessageId(int id) {
		assertTrue(id != 0);
		Collection<Vote> votes = this.voteRepo.findByMessageId(id);
		for (Vote vote : votes) {
			this.delete(vote);
		}
	}
	
	public Collection<Vote> findByBuildId(int id) {
		assertTrue(id != 0);
		Collection<Vote> res = this.voteRepo.findByBuildId(id);
		assertNotNull(res);
		return res;
	}

	public Integer getPunctuationBuild(Build build) {
		assertNotNull(build);
		assertTrue(build.getId() != 0);
		Integer positives = this.voteRepo.countPositivesVotesByBuild(build.getId());
		Integer negatives = this.voteRepo.countNegativesVotesByBuild(build.getId());

		return positives - negatives;

	}

	public void createUpVoteByBuildId(int buildId) {
		assertTrue(buildId != 0);
		Build build = buildService.findBuildById(buildId);
		assertNotNull(build);
		Summoner voter = this.summonerService.findByPrincipal();
		assertNotNull(voter);
		Collection<Vote> votes = voteRepo.findByBuildId(buildId);
		assertNotNull(votes);
		for (Vote v : votes) {
			if (v.getVoter().getId().equals(voter.getId())) {
				if (v.isStatus() == false) {
					delete(v);
				} else {
					assertNotEquals("HAS VUELTO A VOTAR LO MISMO", v.isStatus(), true);
				}
			}
		}
		Vote vote = new Vote(voter, build, null, null, true);
		save(vote);
	}

	public void createDownVoteByBuildId(int buildId) {
		assertTrue(buildId != 0);
		Build build = buildService.findBuildById(buildId);
		assertNotNull(build);
		Summoner voter = this.summonerService.findByPrincipal();
		assertNotNull(voter);
		Collection<Vote> votes = voteRepo.findByBuildId(buildId);
		assertNotNull(votes);
		for (Vote v : votes) {
			if (v.getVoter().getId().equals(voter.getId())) {
				if (v.isStatus() == true) {
					delete(v);
				} else {
					assertNotEquals("HAS VUELTO A VOTAR LO MISMO", v.isStatus(), false);
				}
			}
		}
		Vote vote = new Vote(voter, build, null, null, false);
		save(vote);
	}

	public void deleteByBuildId(int id) {
		assertTrue(id != 0);
		Collection<Vote> votes = this.voteRepo.findByBuildId(id);
		for (Vote vote : votes) {
			this.delete(vote);
		}
	}

	public void save(Vote vote) {
		assertNotNull(vote);
		this.voteRepo.save(vote);
	}

	public void delete(Vote vote) {
		this.voteRepo.delete(vote);
	}

}
