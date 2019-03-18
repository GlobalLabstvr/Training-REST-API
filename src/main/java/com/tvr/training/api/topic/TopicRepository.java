package com.tvr.training.api.topic;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface TopicRepository extends JpaRepository<Topic, Long> {

	Optional<Topic> findById(Long topicId);

	List<Topic> findBySubjectId(Long subjectId);
	
	List<Topic> findByNameContaining(String name);
	
}
