package org.springframework.samples.theionianbookshelf.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.theionianbookshelf.model.Administrator;

public interface AdministratorRepository extends CrudRepository<Administrator, Integer> {

}
