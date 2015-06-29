package com.univer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.univer.domain.Teacher;
import com.univer.repository.TeacherRepository;
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
 * REST controller for managing Teacher.
 */
@RestController
@RequestMapping("/api")
public class TeacherResource {

    private final Logger log = LoggerFactory.getLogger(TeacherResource.class);

    @Inject
    private TeacherRepository teacherRepository;

    /**
     * POST  /teachers -> Create a new teacher.
     */
    @RequestMapping(value = "/teachers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Teacher teacher) throws URISyntaxException {
        log.debug("REST request to save Teacher : {}", teacher);
        if (teacher.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new teacher cannot already have an ID").build();
        }
        teacherRepository.save(teacher);
        return ResponseEntity.created(new URI("/api/teachers/" + teacher.getId())).build();
    }

    /**
     * PUT  /teachers -> Updates an existing teacher.
     */
    @RequestMapping(value = "/teachers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Teacher teacher) throws URISyntaxException {
        log.debug("REST request to update Teacher : {}", teacher);
        if (teacher.getId() == null) {
            return create(teacher);
        }
        teacherRepository.save(teacher);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /teachers -> get all the teachers.
     */
    @RequestMapping(value = "/teachers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Teacher> getAll() {
        log.debug("REST request to get all Teachers");
        return teacherRepository.findAll();
    }

    /**
     * GET  /teachers/:id -> get the "id" teacher.
     */
    @RequestMapping(value = "/teachers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Teacher> get(@PathVariable Long id) {
        log.debug("REST request to get Teacher : {}", id);
        return Optional.ofNullable(teacherRepository.findOne(id))
            .map(teacher -> new ResponseEntity<>(
                teacher,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /teachers/:id -> delete the "id" teacher.
     */
    @RequestMapping(value = "/teachers/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Teacher : {}", id);
        teacherRepository.delete(id);
    }
}
