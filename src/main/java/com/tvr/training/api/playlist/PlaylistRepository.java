package com.tvr.training.api.playlist;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvr.training.api.topic.Topic;

@Repository

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

	Optional<Playlist> findById(Long playlistId);

	List<Playlist> findByTopicId(Long topicId);
	
	List<Playlist> findByNameContaining(String name);
	
}
