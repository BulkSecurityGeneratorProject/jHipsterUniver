package com.univer.web.rest;

import com.univer.Application;
import com.univer.domain.Faculty;
import com.univer.repository.FacultyRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FacultyResource REST controller.
 *
 * @see FacultyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FacultyResourceTest {

    private static final String DEFAULT_TITLE = "SAMPLE_TEXT";
    private static final String UPDATED_TITLE = "UPDATED_TEXT";

    @Inject
    private FacultyRepository facultyRepository;

    private MockMvc restFacultyMockMvc;

    private Faculty faculty;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FacultyResource facultyResource = new FacultyResource();
        ReflectionTestUtils.setField(facultyResource, "facultyRepository", facultyRepository);
        this.restFacultyMockMvc = MockMvcBuilders.standaloneSetup(facultyResource).build();
    }

    @Before
    public void initTest() {
        faculty = new Faculty();
        faculty.setTitle(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void createFaculty() throws Exception {
        int databaseSizeBeforeCreate = facultyRepository.findAll().size();

        // Create the Faculty
        restFacultyMockMvc.perform(post("/api/facultys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(faculty)))
                .andExpect(status().isCreated());

        // Validate the Faculty in the database
        List<Faculty> facultys = facultyRepository.findAll();
        assertThat(facultys).hasSize(databaseSizeBeforeCreate + 1);
        Faculty testFaculty = facultys.get(facultys.size() - 1);
        assertThat(testFaculty.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void getAllFacultys() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

        // Get all the facultys
        restFacultyMockMvc.perform(get("/api/facultys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(faculty.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())));
    }

    @Test
    @Transactional
    public void getFaculty() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

        // Get the faculty
        restFacultyMockMvc.perform(get("/api/facultys/{id}", faculty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(faculty.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFaculty() throws Exception {
        // Get the faculty
        restFacultyMockMvc.perform(get("/api/facultys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFaculty() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

		int databaseSizeBeforeUpdate = facultyRepository.findAll().size();

        // Update the faculty
        faculty.setTitle(UPDATED_TITLE);
        restFacultyMockMvc.perform(put("/api/facultys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(faculty)))
                .andExpect(status().isOk());

        // Validate the Faculty in the database
        List<Faculty> facultys = facultyRepository.findAll();
        assertThat(facultys).hasSize(databaseSizeBeforeUpdate);
        Faculty testFaculty = facultys.get(facultys.size() - 1);
        assertThat(testFaculty.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void deleteFaculty() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

		int databaseSizeBeforeDelete = facultyRepository.findAll().size();

        // Get the faculty
        restFacultyMockMvc.perform(delete("/api/facultys/{id}", faculty.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Faculty> facultys = facultyRepository.findAll();
        assertThat(facultys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
