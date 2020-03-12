package the_ionian_bookshelf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import the_ionian_bookshelf.model.Champion;
import the_ionian_bookshelf.model.ChangeRequest;
import the_ionian_bookshelf.model.Item;
import the_ionian_bookshelf.repository.ChangeRequestRepository;

@Service
public class ChangeRequestService {

	@Autowired
	private ChangeRequestRepository changeRepository;
	
	@Transactional(readOnly = true)
	public Champion findChampionById(final int championId) throws DataAccessException {
		return this.changeRepository.findChampionById(championId);
	}
	
	@Transactional(readOnly = true)
	public Item findItemById(final int itemId) throws DataAccessException {
		return this.changeRepository.findItemById(itemId);
	}
	
	@Transactional
	public void saveChangeRequest(final ChangeRequest changeRequest) throws DataAccessException {
		this.changeRepository.save(changeRequest);
	}

}
