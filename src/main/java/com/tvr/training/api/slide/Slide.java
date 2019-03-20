package com.tvr.training.api.slide;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tvr.training.api.document.Document;
import com.tvr.training.api.playlist.Playlist;
import com.tvr.training.api.program.Program;
import com.tvr.training.api.sites.Site;
import com.tvr.training.api.topic.Topic;


@Entity
@Table(name = "slides")
public class Slide  {
	@Id
	@Column(name = "SlideID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String name;

    @NotNull
    @Size(max = 250)
    private String description;
    
    @NotNull
    @Size(max = 250)
    private String master;
    
    
    @Size(max = 250)
    private String image;
    

	@NotNull
    @Size(max = 250)
    private String student;
    
	@ManyToOne(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
	@JoinColumn(name = "ProgramID")
    private Program program;
    
	@ManyToOne(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
	@JoinColumn(name = "PlaylistID")
    private Playlist playlist;
    
	@ManyToOne(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
	@JoinColumn(name = "SiteID")
    private Site site;
    
	@ManyToOne(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
	@JoinColumn(name = "DocumentID")
    private Document document;
    
        
    @ManyToOne(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    @JoinColumn(name = "TopicID")
    private Topic topic;

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
    
    public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getStudent() {
		return student;
	}

	public void setStudent(String student) {
		this.student = student;
	}
	
  
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

}