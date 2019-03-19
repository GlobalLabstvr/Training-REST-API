package com.tvr.training.api.document;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvr.training.api.document.Document;

@Repository

public interface DocumentRepository extends JpaRepository<Document, Long> {

	Optional<Document> findById(Long documentId);

	List<Document> findByTopicId(Long topicId);
	List<Document> findByNameContaining(String name);
	
	
}
