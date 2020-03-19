package the_ionian_bookshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import the_ionian_bookshelf.model.Thread;

public interface ThreadRepository extends JpaRepository<Thread, Integer> {

}
