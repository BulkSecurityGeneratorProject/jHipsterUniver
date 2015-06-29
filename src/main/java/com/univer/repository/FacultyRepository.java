package com.univer.repository;

import com.univer.domain.Faculty;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Faculty entity.
 */
public interface FacultyRepository extends JpaRepository<Faculty,Long> {

}
