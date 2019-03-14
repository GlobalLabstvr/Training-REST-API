package com.tvr.training.api.master;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MasterRepository extends JpaRepository<Master, Long> {

	Optional<Master> findById(Long masterId);

	List<Master> findBySlideId(Long slideId);
	
}
