package the_ionian_bookshelf.repository;

import org.springframework.data.repository.CrudRepository;

import the_ionian_bookshelf.model.Branch;

public interface BranchRepository extends CrudRepository<Branch, Integer> {

}
