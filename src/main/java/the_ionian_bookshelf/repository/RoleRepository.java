package the_ionian_bookshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import the_ionian_bookshelf.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("select role from Role role where role.name = 'default'")
    Role findDefaultRole();

}
