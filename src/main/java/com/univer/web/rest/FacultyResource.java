package com.univer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.univer.domain.Faculty;
import com.univer.repository.FacultyRepository;
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
 * REST controller for managing Faculty.
 */
@RestController
@RequestMapping("/api")
public class FacultyResource {

    private final Logger log = LoggerFactory.getLogger(FacultyResource.class);

    @Inject
    private FacultyRepository facultyRepository;

    /**
     * POST  /facultys -> Create a new faculty.
     */
    @RequestMapping(value = "/facultys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Faculty faculty) throws URISyntaxException {
        log.debug("REST request to save Faculty : {}", faculty);
        if (faculty.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new faculty cannot already have an ID").build();
        }
        facultyRepository.save(faculty);
        return ResponseEntity.created(new URI("/api/facultys/" + faculty.getId())).build();
    }

    /**
     * PUT  /facultys -> Updates an existing faculty.
     */
    @RequestMapping(value = "/facultys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Faculty faculty) throws URISyntaxException {
        log.debug("REST request to update Faculty : {}", faculty);
        if (faculty.getId() == null) {
            return create(faculty);
        }
        facultyRepository.save(faculty);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /facultys -> get all the facultys.
     */
    @RequestMapping(value = "/facultys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Faculty> getAll() {
        log.debug("REST request to get all Facultys");
        return facultyRepository.findAll();
    }

    /**
     * GET  /facultys/:id -> get the "id" faculty.
     */
    @RequestMapping(value = "/facultys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Faculty> get(@PathVariable Long id) {
        log.debug("REST request to get Faculty : {}", id);
        return Optional.ofNullable(facultyRepository.findOne(id))
            .map(faculty -> new ResponseEntity<>(
                faculty,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /facultys/:id -> delete the "id" faculty.
     */
    @RequestMapping(value = "/facultys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Faculty : {}", id);
        facultyRepository.delete(id);
    }
}
