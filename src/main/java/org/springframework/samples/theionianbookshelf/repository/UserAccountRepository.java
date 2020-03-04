package org.springframework.samples.theionianbookshelf.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.theionianbookshelf.model.UserAccount;

public interface UserAccountRepository extends CrudRepository<UserAccount, String> {

}
