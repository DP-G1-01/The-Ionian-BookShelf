package org.springframework.samples.the_ionian_bookshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;

public interface ChampionRepository extends JpaRepository<Champion, Integer> {

}
