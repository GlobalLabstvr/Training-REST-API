package com.tvr.training.api.slide;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tvr.training.api.exception.ResourceNotFoundException;
import com.tvr.training.api.topic.TopicRepository;

@RestController
public class SlideController {

    @Autowired
    private SlideRepository slideRepository;

    @Autowired
    private TopicRepository topicRepository;
    
    @GetMapping("/slides")
    public List<Slide> getAllSlides( ) {
        return slideRepository.findAll( );
    }
    
    @GetMapping("slides/{slideId}")
    public Optional<Slide> getSlidelist(
    		@PathVariable (value = "slideId") Long slideId) {
        return slideRepository.findById(slideId);
    }
    
    @GetMapping("/topics/{topicId}/slides")
    public List<Slide> getSlidesByTopicId(
    		@PathVariable (value = "topicId") Long topicId) {
        return slideRepository.findByTopicId(topicId);
    }
    
       
    @PostMapping("/topics/{topicId}/slides")
    public Slide createSlide(
    		@PathVariable (value = "topicId") Long topicId,
    		@Valid @RequestBody Slide slide) {
        return topicRepository.findById(topicId).map(topic -> {
            slide.setTopic(topic);         
            return slideRepository.save(slide);
        }).orElseThrow(() -> new ResourceNotFoundException("topicId " + topicId + " not found"));
    }

}
