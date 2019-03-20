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

import com.tvr.training.api.exception.ResourceNotFoundException;
import com.tvr.training.api.slide.Slide;
import com.tvr.training.api.topic.TopicRepository;

@RestController
public class SlideController {

    @Autowired
    private SlideRepository slideRepository;

    @Autowired
    private TopicRepository topicRepository;
    
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
        return topicRepository.findById(topicId).map(topic -> {
            slide.setTopic(topic);         
            return slideRepository.save(slide);
        }).orElseThrow(() -> new ResourceNotFoundException("topicId " + topicId + " not found"));
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
