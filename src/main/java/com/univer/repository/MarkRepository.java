package com.univer.repository;

import com.univer.domain.Mark;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Mark entity.
 */
public interface MarkRepository extends JpaRepository<Mark,Long> {

}
