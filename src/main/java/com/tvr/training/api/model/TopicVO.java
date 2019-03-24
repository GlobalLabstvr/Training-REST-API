package com.tvr.training.api.model;

import java.util.ArrayList;
import java.util.List;

import com.tvr.training.api.subject.Subject;

public class TopicVO {
	

	    private Long id; 

	    private String name;

	    private String description;

	    private Subject subject;
	    
	    private List<ResourceVO> playlists = new ArrayList<>();
	    
	    private List<ProgramVO> programs = new ArrayList<>();
	    
	    private List<DocumentVO> documents = new ArrayList<>();
	    
	    private List<SiteVO> sites = new ArrayList<>();

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

		public Subject getSubject() {
			return subject;
		}

		public void setSubject(Subject subject) {
			this.subject = subject;
		}

		public List<ResourceVO> getPlaylists() {
			return playlists;
		}

		public void setPlaylists(List<ResourceVO> playlists) {
			this.playlists = playlists;
		}

		public List<ProgramVO> getPrograms() {
			return programs;
		}

		public void setPrograms(List<ProgramVO> programs) {
			this.programs = programs;
		}

		public List<DocumentVO> getDocuments() {
			return documents;
		}

		public void setDocuments(List<DocumentVO> documents) {
			this.documents = documents;
		}

		public List<SiteVO> getSites() {
			return sites;
		}

		public void setSites(List<SiteVO> sites) {
			this.sites = sites;
		}
	    
}
