package org.springframework.samples.the_ionian_bookshelf.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.the_ionian_bookshelf.model.Authorities;

public interface AuthoritiesRepository extends CrudRepository<Authorities, String> {

}
