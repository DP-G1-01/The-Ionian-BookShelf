package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.the_ionian_bookshelf.repository.ThreadRepository;
import org.springframework.samples.the_ionian_bookshelf.model.Build;
import org.springframework.samples.the_ionian_bookshelf.model.League;
import org.springframework.samples.the_ionian_bookshelf.model.Message;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.repository.ThreadRepository;
import org.springframework.stereotype.Service;

@Service
public class ThreadService {

    @Autowired
    private ThreadRepository threadRepo;
    @Autowired
    private MessageService messageService;
    @Autowired
    private AuthoritiesService authService;
    @Autowired
    private LeagueService leagueService;
    @Autowired
    private BuildService buildService;

    @Autowired
    public ThreadService(ThreadRepository threadRepository) {
        this.threadRepo = threadRepository;
    }

    public Thread create() {
        Thread res = new Thread();
        res.setTitle("New Thread");
        res.setDescription("New description");
        return res;
    }

    @Transactional
    public Collection<Thread> findAll() {
        Collection<Thread> res = this.threadRepo.findAll();
        assertNotNull(res);
        return res;
    }

    @Transactional
    public Thread findOne(int id) {
        assertTrue(id != 0);
        final Thread res = this.threadRepo.findById(id).get();
        assertNotNull(res);
        return res;

    }

    public Thread save(Thread thread) throws DataAccessException {
        assertNotNull(thread);
        assertTrue(this.authService.checkAuthorities("administrator") || this.authService.checkAuthorities("summoner")
                || this.authService.checkAuthorities("reviewer"));
        return this.threadRepo.save(thread);
    }

    public void delete(Thread thread) throws DataAccessException {
        assertNotNull(thread);
        assertTrue(this.authService.checkAuthorities("administrator") || this.authService.checkAuthorities("reviewer"));
        assertFalse("NO SE PUEDE ELIMINAR UN THREAD VINCULADO A UNA LIGA", isAThreadFromLeague(thread));
        assertFalse("NO SE PUEDE ELIMINAR UN THREAD VINCULADO A UNA BUILD", isAThreadFromBuild(thread));
        this.threadRepo.delete(thread);
    }

    // public void deleteFromVote(Thread thread) throws DataAccessException {
    // assertNotNull(thread);
    // Collection<Vote> votes = this.voteService.findByThread(thread);
    // for (Vote vote : votes) {
    // this.voteService.delete(vote);
    // }
    // }

    public void deleteFromMessages(Thread thread) throws DataAccessException {
        assertNotNull(thread);
        assertTrue(this.authService.checkAuthorities("administrator"));
        Collection<Message> messages = this.messageService.findByThread(thread);
        for (Message message : messages) {
            this.messageService.delete(message);
        }
    }

    public boolean isAThreadFromLeague(Thread thread) throws DataAccessException {
        assertNotNull(thread);
        assertTrue(thread.getId() != 0);
        boolean res = true;
        League league = this.leagueService.findByThread(thread);
        if (league == null) {
            res = false;
        }
        return res;
    }

    public boolean isAThreadFromBuild(Thread thread) throws DataAccessException {
        assertNotNull(thread);
        assertTrue(thread.getId() != 0);
        boolean res = true;
        Build build = this.buildService.findByThread(thread);
        if (build == null) {
            res = false;
        }
        return res;
    }

}
