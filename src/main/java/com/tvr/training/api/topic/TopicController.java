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

import com.tvr.training.api.exception.ResourceNotFoundException;
import com.tvr.training.api.subject.Subject;
import com.tvr.training.api.subject.SubjectRepository;

@RestController
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SubjectRepository subjectRepository;
    
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
    
    @GetMapping("topics/{topicId}")
    public Optional<Topic> getTopic(
    		@PathVariable (value = "topicId") Long topicId) {
        return topicRepository.findById(topicId);
    }
    
       
    @PostMapping("/topics/{subjectId}")
    public Topic createSubject(
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

}
