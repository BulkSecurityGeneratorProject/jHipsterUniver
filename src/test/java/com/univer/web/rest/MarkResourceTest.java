package com.univer.web.rest;

import com.univer.Application;
import com.univer.domain.Mark;
import com.univer.repository.MarkRepository;

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
 * Test class for the MarkResource REST controller.
 *
 * @see MarkResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MarkResourceTest {

    private static final String DEFAULT_RATING = "SAMPLE_TEXT";
    private static final String UPDATED_RATING = "UPDATED_TEXT";

    @Inject
    private MarkRepository markRepository;

    private MockMvc restMarkMockMvc;

    private Mark mark;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MarkResource markResource = new MarkResource();
        ReflectionTestUtils.setField(markResource, "markRepository", markRepository);
        this.restMarkMockMvc = MockMvcBuilders.standaloneSetup(markResource).build();
    }

    @Before
    public void initTest() {
        mark = new Mark();
        mark.setRating(DEFAULT_RATING);
    }

    @Test
    @Transactional
    public void createMark() throws Exception {
        int databaseSizeBeforeCreate = markRepository.findAll().size();

        // Create the Mark
        restMarkMockMvc.perform(post("/api/marks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mark)))
                .andExpect(status().isCreated());

        // Validate the Mark in the database
        List<Mark> marks = markRepository.findAll();
        assertThat(marks).hasSize(databaseSizeBeforeCreate + 1);
        Mark testMark = marks.get(marks.size() - 1);
        assertThat(testMark.getRating()).isEqualTo(DEFAULT_RATING);
    }

    @Test
    @Transactional
    public void getAllMarks() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        // Get all the marks
        restMarkMockMvc.perform(get("/api/marks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(mark.getId().intValue())))
                .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.toString())));
    }

    @Test
    @Transactional
    public void getMark() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        // Get the mark
        restMarkMockMvc.perform(get("/api/marks/{id}", mark.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(mark.getId().intValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMark() throws Exception {
        // Get the mark
        restMarkMockMvc.perform(get("/api/marks/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMark() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

		int databaseSizeBeforeUpdate = markRepository.findAll().size();

        // Update the mark
        mark.setRating(UPDATED_RATING);
        restMarkMockMvc.perform(put("/api/marks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mark)))
                .andExpect(status().isOk());

        // Validate the Mark in the database
        List<Mark> marks = markRepository.findAll();
        assertThat(marks).hasSize(databaseSizeBeforeUpdate);
        Mark testMark = marks.get(marks.size() - 1);
        assertThat(testMark.getRating()).isEqualTo(UPDATED_RATING);
    }

    @Test
    @Transactional
    public void deleteMark() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

		int databaseSizeBeforeDelete = markRepository.findAll().size();

        // Get the mark
        restMarkMockMvc.perform(delete("/api/marks/{id}", mark.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Mark> marks = markRepository.findAll();
        assertThat(marks).hasSize(databaseSizeBeforeDelete - 1);
    }
}
