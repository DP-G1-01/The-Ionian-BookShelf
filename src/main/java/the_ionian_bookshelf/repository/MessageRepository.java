package the_ionian_bookshelf.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import the_ionian_bookshelf.model.Message;
import the_ionian_bookshelf.model.Thread;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select message from Message message where message.thread = ?1")
	Collection<Message> findByThread(Thread thread);

}
