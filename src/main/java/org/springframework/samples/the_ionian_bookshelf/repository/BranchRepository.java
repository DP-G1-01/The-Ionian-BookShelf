package org.springframework.samples.the_ionian_bookshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.samples.the_ionian_bookshelf.model.Branch;

public interface BranchRepository extends JpaRepository<Branch, Integer> {

	@Query("select branch from Branch branch where branch.name = 'default'")
	Branch findDefaultBranch();
}
