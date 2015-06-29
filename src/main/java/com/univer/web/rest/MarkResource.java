package com.univer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.univer.domain.Mark;
import com.univer.repository.MarkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Mark.
 */
@RestController
@RequestMapping("/api")
public class MarkResource {

    private final Logger log = LoggerFactory.getLogger(MarkResource.class);

    @Inject
    private MarkRepository markRepository;

    /**
     * POST  /marks -> Create a new mark.
     */
    @RequestMapping(value = "/marks",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Mark mark) throws URISyntaxException {
        log.debug("REST request to save Mark : {}", mark);
        if (mark.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new mark cannot already have an ID").build();
        }
        markRepository.save(mark);
        return ResponseEntity.created(new URI("/api/marks/" + mark.getId())).build();
    }

    /**
     * PUT  /marks -> Updates an existing mark.
     */
    @RequestMapping(value = "/marks",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Mark mark) throws URISyntaxException {
        log.debug("REST request to update Mark : {}", mark);
        if (mark.getId() == null) {
            return create(mark);
        }
        markRepository.save(mark);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /marks -> get all the marks.
     */
    @RequestMapping(value = "/marks",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Mark> getAll() {
        log.debug("REST request to get all Marks");
        return markRepository.findAll();
    }

    /**
     * GET  /marks/:id -> get the "id" mark.
     */
    @RequestMapping(value = "/marks/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Mark> get(@PathVariable Long id) {
        log.debug("REST request to get Mark : {}", id);
        return Optional.ofNullable(markRepository.findOne(id))
            .map(mark -> new ResponseEntity<>(
                mark,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /marks/:id -> delete the "id" mark.
     */
    @RequestMapping(value = "/marks/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Mark : {}", id);
        markRepository.delete(id);
    }
}
