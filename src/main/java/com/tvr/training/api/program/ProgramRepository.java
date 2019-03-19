package com.tvr.training.api.program;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvr.training.api.program.Program;

@Repository

public interface ProgramRepository extends JpaRepository<Program, Long> {

	Optional<Program> findById(Long programId);

	List<Program> findByTopicId(Long topicId);
	
	List<Program> findByNameContaining(String name);
	
}

