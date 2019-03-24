package com.tvr.training.api.model;

import java.util.ArrayList;
import java.util.List;

import com.tvr.training.api.subject.Subject;

public class SubjectVO {
	
	private Long id;

    private String name;

    private String description;
	    
    private List<TopicVO> topics = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<TopicVO> getTopics() {
		return topics;
	}

	public void setTopics(List<TopicVO> topics) {
		this.topics = topics;
	}

}
