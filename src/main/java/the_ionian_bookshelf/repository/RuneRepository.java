package the_ionian_bookshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import the_ionian_bookshelf.model.Rune;

public interface RuneRepository extends JpaRepository<Rune, Integer> {

}
