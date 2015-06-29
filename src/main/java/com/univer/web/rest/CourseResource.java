package com.univer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.univer.domain.Course;
import com.univer.repository.CourseRepository;
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
 * REST controller for managing Course.
 */
@RestController
@RequestMapping("/api")
public class CourseResource {

    private final Logger log = LoggerFactory.getLogger(CourseResource.class);

    @Inject
    private CourseRepository courseRepository;

    /**
     * POST  /courses -> Create a new course.
     */
    @RequestMapping(value = "/courses",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Course course) throws URISyntaxException {
        log.debug("REST request to save Course : {}", course);
        if (course.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new course cannot already have an ID").build();
        }
        courseRepository.save(course);
        return ResponseEntity.created(new URI("/api/courses/" + course.getId())).build();
    }

    /**
     * PUT  /courses -> Updates an existing course.
     */
    @RequestMapping(value = "/courses",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Course course) throws URISyntaxException {
        log.debug("REST request to update Course : {}", course);
        if (course.getId() == null) {
            return create(course);
        }
        courseRepository.save(course);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /courses -> get all the courses.
     */
    @RequestMapping(value = "/courses",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Course> getAll() {
        log.debug("REST request to get all Courses");
        return courseRepository.findAll();
    }

    /**
     * GET  /courses/:id -> get the "id" course.
     */
    @RequestMapping(value = "/courses/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Course> get(@PathVariable Long id) {
        log.debug("REST request to get Course : {}", id);
        return Optional.ofNullable(courseRepository.findOne(id))
            .map(course -> new ResponseEntity<>(
                course,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /courses/:id -> delete the "id" course.
     */
    @RequestMapping(value = "/courses/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Course : {}", id);
        courseRepository.delete(id);
    }
}
