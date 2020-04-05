package org.springframework.samples.the_ionian_bookshelf.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.samples.the_ionian_bookshelf.model.Build;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;

public interface BuildRepository extends JpaRepository<Build, Integer> {

    Build findBuildById(int id);

    void removeBuildById(int id);

    @Query("select distinct build from Build build " + "join build.items item where item.id = ?1")
    List<Build> findBuildsByItemId(int id);

    @Query("SELECT b FROM Build b where b.visibility= true")
    Collection<Build> findAllPublics();

    @Query("SELECT b FROM Build b where b.summoner.id= ?1")
    Collection<Build> findBuildsBySummonerId(int summonerId);

    @Query("SELECT b FROM Build b WHERE b.runePage.id = ?1")
    Collection<Build> findAllByRunePage(Integer id);

    @Query("SELECT b FROM Build b WHERE b.champion.id = ?1")
    Collection<Build> findAllByChampion(Integer id);

    @Query("SELECT b FROM Build b WHERE b.thread = ?1")
    Build findByThread(Thread thread);
}
