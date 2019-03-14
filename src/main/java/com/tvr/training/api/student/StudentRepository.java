package com.tvr.training.api.student;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface StudentRepository extends JpaRepository<Student, Long> {

	Optional<Student> findById(Long masterId);

	List<Student> findBySlideId(Long slideId);
	
}
