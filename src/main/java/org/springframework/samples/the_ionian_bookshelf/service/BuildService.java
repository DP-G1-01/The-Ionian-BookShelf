package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Build;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.samples.the_ionian_bookshelf.model.RunePage;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.repository.BuildRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.ChampionRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.ItemRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.RunePageRepository;
import org.springframework.stereotype.Service;

@Service
public class BuildService {

    @Autowired
    private BuildRepository buildRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private RunePageRepository runePageRepository;
    
    @Autowired
	private VoteService voteService;

    public Build create() {

        final Build res = new Build();

        return res;
    }

    public Collection<Build> findAllPublics() {

        final Collection<Build> res = this.buildRepository.findAllPublics();

        assertNotNull(res);

        return res;
    }

    public Collection<Build> findMineBuilds(int summonerId) {

        final Collection<Build> res = this.buildRepository.findBuildsBySummonerId(summonerId);

        assertNotNull(res);

        return res;
    }

    public Build findOne(final int id) {

        assertTrue(id != 0);

        final Build res = this.buildRepository.findById(id).get();
        assertNotNull(res);

        return res;
    }

    public Build findBuildById(int buildId) {
        Build build = buildRepository.findBuildById(buildId);
        assertNotNull(build);
        return build;
    }

    public void removeBuildById(int buildId) {
        Build build = buildRepository.findBuildById(buildId);
        assertNotNull(build);
        this.voteService.deleteByBuildId(build.getId());
        buildRepository.delete(build);
    }

    public void saveBuild(Build i) {
        buildRepository.save(i);
    }

    public Collection<Item> findItems() {
        return itemRepository.findAll();
    }

    public Collection<Champion> findChampions() {
        return championRepository.findAll();
    }

    public Collection<RunePage> findRunePages() {
        return runePageRepository.findAll();
    }

    public Build findByThread(Thread thread) {
        assertNotNull(thread);
        Build res = this.buildRepository.findByThread(thread);
        // no controlamos si res es null porque en el caso de que thread no tenga league
        // asociada, res es null
        return res;
    }
}
