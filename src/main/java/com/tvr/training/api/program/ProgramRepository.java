package com.tvr.training.api.program;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProgramRepository extends JpaRepository<Program, Long> {

	Optional<Program> findById(Long programId);

	List<Program> findByTopicId(Long topicId);
	
}

