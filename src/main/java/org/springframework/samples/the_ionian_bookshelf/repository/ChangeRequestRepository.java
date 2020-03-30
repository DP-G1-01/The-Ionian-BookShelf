package org.springframework.samples.the_ionian_bookshelf.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.samples.the_ionian_bookshelf.model.ChangeRequest;

public interface ChangeRequestRepository extends JpaRepository<ChangeRequest, Integer> {
	
	@Query("SELECT r FROM ChangeRequest r where r.champion.id= ?1")
	ChangeRequest findChangeRequestByChampionId(int championId);
	
	@Query("SELECT r FROM ChangeRequest r where r.item.id= ?1")
	ChangeRequest findChangeRequestByItemId(int itemId);

	@Query("SELECT r FROM ChangeRequest r where r.summoner.id= ?1")
	Collection<ChangeRequest> findChangeRequestsBySummonerId(int summonerId);

	ChangeRequest findChangeRequestById(int id);
	

}
