package the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import the_ionian_bookshelf.model.Actor;
import the_ionian_bookshelf.model.Message;
import the_ionian_bookshelf.model.Summoner;
import the_ionian_bookshelf.model.Thread;
import the_ionian_bookshelf.model.Vote;
import the_ionian_bookshelf.repository.MessageRepository;
import the_ionian_bookshelf.security.LoginService;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messRepo;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private SummonerService summonerService;

    @Autowired
    public MessageService(MessageRepository messRepo) {
        this.messRepo = messRepo;
    }

    public Message create(int threadId) {

        Message res = new Message();
        Date moment;
        moment = new Date(System.currentTimeMillis() - 1);
        res.setMoment(moment);
        Thread thread = this.threadService.findOne(threadId);
        res.setThread(thread);
        Summoner principal = this.summonerService.findByPrincipal();
        res.setSummoner(principal);

        return res;
    }

    @Transactional
    public Iterable<Message> findAll() {
        Iterable<Message> res = this.messRepo.findAll();
        assertNotNull(res);
        return res;
    }

    @Transactional
    public Message findOneMesageById(int id) {
        assertTrue(id != 0);
        final Message res = this.messRepo.findById(id).get();
        assertNotNull(res);
        return res;
    }

    public Message saveMessage(Message message) throws DataAccessException {
        assertNotNull(message);
        // Actor principal = this.actorService.findByPrincipal();
        //
        // assertTrue(this.actorService.checkAuthority(principal,
        // Authority.ADMINISTRATOR)
        // || this.actorService.checkAuthority(principal, Authority.REVIEWER)
        // || this.actorService.checkAuthority(principal, Authority.SUMMONER));

        return this.messRepo.save(message);
    }

    public void delete(Message message) throws DataAccessException {
        assertNotNull(message);
        // Actor principal = this.actorService.findByPrincipal();
        //
        // assertTrue(this.actorService.checkAuthority(principal,
        // Authority.ADMINISTRATOR)
        // || this.actorService.checkAuthority(principal, Authority.REVIEWER));
        this.messRepo.delete(message);
    }

    public Collection<Message> findByThread(Thread thread) {
        assertNotNull(thread);
        assertTrue(thread.getId() != 0);

        Collection<Message> res = this.messRepo.findByThread(thread);
        assertNotNull(res);

        return res;
    }

}
