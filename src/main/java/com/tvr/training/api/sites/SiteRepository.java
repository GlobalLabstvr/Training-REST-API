package com.tvr.training.api.sites;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvr.training.api.sites.Site;

@Repository

public interface SiteRepository extends JpaRepository<Site, Long> {

	Optional<Site> findById(Long siteId);

	List<Site> findByTopicId(Long topicId);
	List<Site> findByNameContaining(String name);
	
	
}
