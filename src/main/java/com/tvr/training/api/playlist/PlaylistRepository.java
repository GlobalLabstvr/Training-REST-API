package com.tvr.training.api.playlist;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

	Optional<Playlist> findById(Long playlistId);

	List<Playlist> findByTopicId(Long topicId);
	
}
