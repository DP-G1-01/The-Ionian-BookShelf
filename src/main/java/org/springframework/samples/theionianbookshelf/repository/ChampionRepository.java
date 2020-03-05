package org.springframework.samples.theionianbookshelf.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.theionianbookshelf.model.Champion;

public interface ChampionRepository extends CrudRepository<Champion, Integer> {

}
