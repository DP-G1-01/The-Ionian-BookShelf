package the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import the_ionian_bookshelf.model.Branch;
import the_ionian_bookshelf.model.Build;
import the_ionian_bookshelf.model.Rune;
import the_ionian_bookshelf.model.RunePage;
import the_ionian_bookshelf.model.Summoner;
import the_ionian_bookshelf.repository.BranchRepository;
import the_ionian_bookshelf.repository.BuildRepository;
import the_ionian_bookshelf.repository.RunePageRepository;
import the_ionian_bookshelf.repository.RuneRepository;

@Service
public class RunePageService {

	@Autowired
	private RunePageRepository runePageRepository;

	@Autowired
	private SummonerService summonerService;

	@Autowired
	private RuneRepository runeRepository;

	@Autowired
	private BuildRepository buildRepository;

	@Autowired
	private BranchRepository branchRepository;

	@Transactional()
	public Collection<Branch> findBranches() throws DataAccessException {
		return this.branchRepository.findAll();
	}

	// Método para listar runas
	@Transactional
	public Collection<RunePage> findAllMine() throws DataAccessException {
		List<RunePage> runePages = new ArrayList<>();
		Summoner principal = this.summonerService.findByPrincipal();
		this.runePageRepository.findAllBySummonerId(principal.getId()).forEach(runePages::add);
		return runePages;
	}

	@Transactional
	public void save(RunePage runePage) throws DataAccessException {
		assertNotNull(runePage);
		assertNotNull(this.summonerService.findByPrincipal());
		this.runePageRepository.save(runePage);
	}

	@Transactional
	public void delete(RunePage runePage) throws DataAccessException {
		assertNotNull(runePage);
		Summoner principal = this.summonerService.findByPrincipal();
		assertTrue(principal.equals(runePage.getSummoner()));
		Collection<Build> builds = this.buildRepository.findAllByRunePage(runePage.getId());
		builds.forEach(x -> this.buildRepository.delete(x));
		this.runePageRepository.delete(runePage);
	}

	@Transactional
	public RunePage findOne(int id) {

		assertTrue(id != 0);

		RunePage res = this.runePageRepository.findById(id).get();
		assertNotNull(res);
		return res;
	}

	public List<Rune> findRunes() {

		return this.runeRepository.findAll();
	}

	@Transactional
	public RunePage create() {
		assertNotNull(this.summonerService.findByPrincipal());
		Summoner summoner = this.summonerService.findByPrincipal();
		RunePage res = new RunePage();
		res.setName("New Rune Page");
		res.setSummoner(summoner);
		return res;
	}

	// Para agrupar runas por rama y nodo
	public List<List<Rune>> findRunesByBranchNode() throws DataAccessException {
		List<Rune> runes = this.runeRepository.findAll();
		List<List<Rune>> result = new ArrayList<>();
		List<Branch> branchReconocidas = new ArrayList<>();
		for (Rune rune : runes) {
			if (!branchReconocidas.contains(rune.getBranch())) {
				branchReconocidas.add(rune.getBranch());
				List<Rune> newListKey = new ArrayList<>();
				List<Rune> newList1 = new ArrayList<>();
				List<Rune> newList2 = new ArrayList<>();
				List<Rune> newList3 = new ArrayList<>();
				switch (rune.getNode()) {
				case "Key":
					newListKey.add(rune);
					break;
				case "1":
					newList1.add(rune);
					break;
				case "2":
					newList2.add(rune);
					break;
				case "3":
					newList3.add(rune);
					break;
				default:
					throw new IllegalArgumentException("Not a valid rune node");
				}
				result.add(newListKey);
				result.add(newList1);
				result.add(newList2);
				result.add(newList3);
			} else {
				// En branchReconocidas irán entrando las branch en el mismo orden que las
				// listas de sus runas
				int index = branchReconocidas.indexOf(rune.getBranch());
				// Se crearán 4 listas por cada branch, 1 por cada nodo
				switch (rune.getNode()) {
				case "Key":
					index = index * 4;
					break;
				case "1":
					index = index * 4 + 1;
					break;
				case "2":
					index = index * 4 + 2;
					break;
				case "3":
					index = index * 4 + 3;
					break;
				default:
					throw new IllegalArgumentException("Not a valid rune node");
				}
				result.get(index).add(rune);
			}
		}
		return result;
	}

	public List<List<Rune>> findSecondaryRunesByBranchNode() {
		List<Rune> runes = this.runeRepository.findAll();
		List<List<Rune>> result = new ArrayList<>();
		List<Branch> branchReconocidas = new ArrayList<>();
		for (Rune rune : runes) {
			if (!branchReconocidas.contains(rune.getBranch())) {
				branchReconocidas.add(rune.getBranch());
				List<Rune> newListSec = new ArrayList<>();
				switch (rune.getNode()) {
				case "Key":
					break;
				case "1":
					newListSec.add(rune);
					break;
				case "2":
					newListSec.add(rune);
					break;
				case "3":
					newListSec.add(rune);
					break;
				default:
					throw new IllegalArgumentException("Not a valid rune node");
				}
				result.add(newListSec);
			} else {
				// En branchReconocidas irán entrando las branch en el mismo orden que las
				// listas de sus runas
				int index = branchReconocidas.indexOf(rune.getBranch());
				switch (rune.getNode()) {
				case "Key":
					break;
				case "1":
					result.get(index).add(rune);
					break;
				case "2":
					result.get(index).add(rune);
					break;
				case "3":
					result.get(index).add(rune);
					break;
				default:
					throw new IllegalArgumentException("Not a valid rune node");
				}
			}
		}
		return result;
	}
}
