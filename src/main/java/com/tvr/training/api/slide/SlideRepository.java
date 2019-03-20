package com.tvr.training.api.slide;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvr.training.api.sites.Site;

@Repository

public interface SlideRepository extends JpaRepository<Slide, Long> {

	Optional<Slide> findById(Long slideId);

	List<Slide> findByTopicId(Long topicId);
	List<Slide> findByNameContaining(String name);
	
}
