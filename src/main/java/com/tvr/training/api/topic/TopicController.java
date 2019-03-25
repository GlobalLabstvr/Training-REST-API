package com.tvr.training.api.topic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tvr.training.api.document.Document;
import com.tvr.training.api.document.DocumentRepository;
import com.tvr.training.api.exception.ResourceNotFoundException;
import com.tvr.training.api.model.DocumentVO;
import com.tvr.training.api.model.PlaylistVO;
import com.tvr.training.api.model.ProgramVO;
import com.tvr.training.api.model.SiteVO;
import com.tvr.training.api.model.SlideVO;
import com.tvr.training.api.model.TopicVO;
import com.tvr.training.api.playlist.Playlist;
import com.tvr.training.api.playlist.PlaylistRepository;
import com.tvr.training.api.program.Program;
import com.tvr.training.api.program.ProgramRepository;
import com.tvr.training.api.sites.Site;
import com.tvr.training.api.sites.SiteRepository;
import com.tvr.training.api.slide.Slide;
import com.tvr.training.api.slide.SlideRepository;
import com.tvr.training.api.subject.SubjectRepository;

@RestController
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SubjectRepository subjectRepository;
    
    @Autowired
    private PlaylistRepository playlistRepository;
    
    @Autowired
    private ProgramRepository programRepository;
    
    @Autowired
    private SiteRepository siteRepository;
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private SlideRepository slideRepository;
    
    @GetMapping("/topics")
    public List<Topic> getAllsubjects(
    		@RequestParam("name") String name) {
        List<Topic> list = new ArrayList<Topic>();
    	if(name!=null && !name.equals("")) {
        	list = topicRepository.findByNameContaining(name);
        }
    	else {
    		list = topicRepository.findAll();
    	}
    	return list;
    }
    
    @GetMapping("/topics/{topicId}")
    public Optional<Topic> getTopicById(
    		@PathVariable (value = "topicId") Long topicId) {
       return topicRepository.findById(topicId);
    }
    
    @GetMapping("/topics/subjects/{subjectId}")
    public TopicVO getTopicBySubjectId(
    		@PathVariable (value = "subjectId") Long subjectId) {
        List<Topic> topic = topicRepository.findBySubjectId(subjectId);
      TopicVO topicVO = copy(topic.get(0));
      
    	List<Playlist>playlists = playlistRepository.findByTopicId(topicVO.getId());
              playlists.forEach(playlist -> {
    			PlaylistVO playlistVO = copy(playlist);
    			topicVO.getPlaylists().add(playlistVO);
    		});
              List <Program>programs = programRepository.findByTopicId(topicVO.getId());
              programs.forEach(program -> {
            	  ProgramVO programVO = copy(program);
            	  topicVO.getPrograms().add(programVO);
              });
              List <Site>sites = siteRepository.findByTopicId(topicVO.getId());
              sites.forEach(site -> {
            	  SiteVO siteVO = copy(site);
            	  topicVO.getSites().add(siteVO);
              
              });
              List <Document>documents = documentRepository.findByTopicId(topicVO.getId());
              documents.forEach(document -> {
            	  DocumentVO documentVO = copy(document);
            	  topicVO.getDocuments().add(documentVO);
              });
              
              List <Slide> slides = slideRepository.findByTopicId(topicVO.getId());
              slides.forEach(slide -> {
            	  SlideVO slideVO = copy(slide);
            	  topicVO.getSlides().add(slideVO);
              });
            
              
    	return topicVO;
    }
    
       
    @PostMapping("/topics/{subjectId}")
    public Topic createTopic(
    		@PathVariable (value = "subjectId") Long subjectId,
    		@Valid @RequestBody Topic topic) {
        return subjectRepository.findById(subjectId).map(subject -> {
            topic.setSubject(subject);         
            return topicRepository.save(topic);
        }).orElseThrow(() -> new ResourceNotFoundException("courseId " + subjectId + " not found"));
    }

    @PutMapping("/topics")
    public Topic updateTopic(@Valid @RequestBody Topic topicRequest) {
    	Long topicId = topicRequest.getId();
        return topicRepository.findById(topicId).map(topic -> {
        	topic.setSubject(topicRequest.getSubject());
            topic.setName(topicRequest.getName());
            topic.setDescription(topicRequest.getDescription());
            return topicRepository.save(topic);
        }).orElseThrow(() -> new ResourceNotFoundException("TopicId " + topicId + "not found"));
    }
    
    private TopicVO copy(Topic topic) {
    	TopicVO topicVO = new TopicVO();
    	topicVO.setId(topic.getId());
    	topicVO.setName(topic.getName());
    	topicVO.setDescription(topic.getDescription());
    	return topicVO;
    }
    
    private PlaylistVO copy(Playlist playlist) {
    	PlaylistVO playlistVO = new PlaylistVO();
    	playlistVO.setId(playlist.getId());
    	playlistVO.setName(playlist.getName());
    	playlistVO.setDescription(playlist.getDescription());
    	playlistVO.setUrl(playlist.getUrl());
    	return playlistVO;
    }
     
    private ProgramVO copy(Program program) {
    	 ProgramVO programVO = new ProgramVO();
    	 programVO.setId(program.getId());
    	 programVO.setName(program.getName());
    	 programVO.setDescription(program.getDescription());
    	 programVO.setUrl(program.getUrl());
    	 return programVO;
     }
     
     private SiteVO copy(Site site) {
    	 SiteVO siteVO = new SiteVO();
    	 siteVO.setId(site.getId());
    	 siteVO.setName(site.getName());
    	 siteVO.setDescription(site.getDescription());
    	 siteVO.setUrl(site.getUrl());
    	 return siteVO;
     }
    
     private DocumentVO copy(Document document) {
    	DocumentVO documentVO = new DocumentVO();
    	documentVO.setId(document.getId());
    	documentVO.setName(document.getName());
    	documentVO.setDescription(document.getDescription());
    	documentVO.setUrl(document.getUrl());
    	return documentVO;
    }
    
    private SlideVO copy(Slide slide) {
    	SlideVO slideVO = new SlideVO();
    	slideVO.setId(slide.getId());
    	slideVO.setName(slide.getName());
    	slideVO.setDescription(slide.getDescription());
    	slideVO.setMaster(slide.getMaster());
    	slideVO.setStudent(slide.getStudent());
    	slideVO.setProgram(""+slide.getProgram().getId());
    	slideVO.setPlaylist(""+slide.getPlaylist().getId());
    	slideVO.setDocument(""+slide.getDocument().getId());
    	slideVO.setSite(""+slide.getSite().getId());
    	
    	
    	return slideVO;
    }
    

}
