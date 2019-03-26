package com.tvr.training.api.slide;

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
import com.tvr.training.api.model.TopicVO;
import com.tvr.training.api.playlist.Playlist;
import com.tvr.training.api.playlist.PlaylistRepository;
import com.tvr.training.api.program.Program;
import com.tvr.training.api.program.ProgramRepository;
import com.tvr.training.api.sites.Site;
import com.tvr.training.api.sites.SiteRepository;
import com.tvr.training.api.topic.Topic;
import com.tvr.training.api.topic.TopicRepository;

@RestController
public class SlideController {

    @Autowired
    private SlideRepository slideRepository;

    @Autowired
    private TopicRepository topicRepository;
    
    @Autowired
    private PlaylistRepository playlistRepository;
    
    @Autowired
    private ProgramRepository programRepository;
    
    @Autowired
    private SiteRepository siteRepository;
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @GetMapping("/slides")
    public List<Slide> getAllSlides(
    		@RequestParam("name") String name) {
        List<Slide> list = new ArrayList<Slide>();
    	if(name!=null && !name.equals("")) {
        	list = slideRepository.findByNameContaining(name);
        }
    	else {
    		list = slideRepository.findAll();
    	}
    	return list;
    }
    
    @GetMapping("slides/{slideId}")
    public Optional<Slide> getSlidelist(
    		@PathVariable (value = "slideId") Long slideId) {
        return slideRepository.findById(slideId);
    }
    
   
    @PostMapping("/slides/{topicId}")
    public Slide createTopic(
    		@PathVariable (value = "topicId") Long topicId,
    		@Valid @RequestBody Slide slide) {
    	
    	System.out.println(slide);
    	Long playlistId = slide.getPlaylist().getId();
    	Long programId = slide.getProgram().getId();
    	Long siteId = slide.getSite().getId();
    	Long documentId = slide.getDocument().getId();
    	
    	Slide slideResult =  topicRepository.findById(topicId).map(topic -> {
			slide.setTopic(topic);
			slide.setDocument(null);
			slide.setPlaylist(null);
			slide.setSite(null);
			slide.setProgram(null);
			return slideRepository.save(slide);
		}).orElseThrow(() -> new ResourceNotFoundException("topic " + topicId + " not found"));
        


    	Playlist playlistResult = null;
    	Site siteResult = null;
    	Document documentResult = null;
    	Program programResult = null;
    	
    	
    	if((playlistResult = playlistRepository.findById(playlistId).get())!=null) {
    		slideResult.setPlaylist(playlistResult);  
    	} 
    	else
    	{
    		throw new ResourceNotFoundException("playlistId " + playlistId + " not found");
    	}
    	
    	if((programResult = programRepository.findById(programId).get())!=null) {
    		slideResult.setProgram(programResult);  
    	} 
    	else
    	{
    		throw new ResourceNotFoundException("programId " + programId + " not found");
    	}
    	
    	if((siteResult = siteRepository.findById(siteId).get())!=null) {
    		slideResult.setSite(siteResult);  
    	} 
    	else
    	{
    		throw new ResourceNotFoundException("siteId " + siteId + " not found");
    	}
    	
    	if((documentResult = documentRepository.findById(documentId).get())!=null) {
    		slideResult.setDocument(documentResult);  
    	} 
    	else
    	{
    		throw new ResourceNotFoundException("documentId " + documentId + " not found");
    	}
    	
       return slideRepository.save(slideResult);
    }
    
    @PutMapping("/slides")
    public Slide updateSlide(@Valid @RequestBody Slide slideRequest) {
    	Long slideId = slideRequest.getId();
        return slideRepository.findById(slideId).map(slide -> {
        	slide.setTopic(slideRequest.getTopic());
            slide.setName(slideRequest.getName());
            slide.setDescription(slideRequest.getDescription());
            slide.setMaster(slideRequest.getMaster());
            slide.setStudent(slideRequest.getStudent());
            
                     
                       
          return slideRepository.save(slide);
        }).orElseThrow(() -> new ResourceNotFoundException("slideId " + slideId + "not found"));
    }

}
