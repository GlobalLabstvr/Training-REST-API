package com.tvr.training.api.subject;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface SubjectRepository extends JpaRepository<Subject, Long> {

	Optional<Subject> findById(Long subjectId);

	List<Subject> findByCourseId(Long courseId);
	
}
