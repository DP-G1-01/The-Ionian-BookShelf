package the_ionian_bookshelf.repository;

import org.springframework.data.repository.CrudRepository;

import the_ionian_bookshelf.model.Champion;

public interface ChampionRepository extends CrudRepository<Champion, Integer> {

}
