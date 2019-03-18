package com.tvr.training.api.topic;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public List<Topic> getAllsubjects( ) {
        return topicRepository.findAll( );
    }
    
    @GetMapping("topics/{topicId}")
    public Optional<Topic> getsubjects(
    		@PathVariable (value = "topicId") Long topicId) {
        return topicRepository.findById(topicId);
    }
    
    @GetMapping("/subjects/{subjectId}/topics")
    public List<Topic> getSubjectsByCourseId(
    		@PathVariable (value = "subjectId") Long subjectId) {
        return topicRepository.findBySubjectId(subjectId);
    }
    
       
    @PostMapping("/subjects/{subjectId}/topics")
    public Topic createSubject(
    		@PathVariable (value = "subjectId") Long subjectId,
    		@Valid @RequestBody Topic topic) {
        return subjectRepository.findById(subjectId).map(subject -> {
            topic.setSubject(subject);         
            return topicRepository.save(topic);
        }).orElseThrow(() -> new ResourceNotFoundException("courseId " + subjectId + " not found"));
    }
    @PutMapping("/subjects/{subjectId}/topics/{topicId}")
    public Topic updateTopic(@PathVariable (value = "subjectId") Long subjectId,
                                 @PathVariable (value = "topicId") Long topicId,
                                 @Valid @RequestBody Topic topicRequest) {
        if(!subjectRepository.existsById(subjectId)) {
            throw new ResourceNotFoundException("subjectId " + subjectId + " not found");
        }

        return topicRepository.findById(topicId).map(topic -> {
            topic.setName(topicRequest.getName());
            topic.setDescription(topicRequest.getDescription());
            return topicRepository.save(topic);
        }).orElseThrow(() -> new ResourceNotFoundException("TopicId " + topicId + "not found"));
    }

   

   

   /* @DeleteMapping("/courses/{courseId}/subjects/{subjectId}")
    public ResponseEntity<Topic> deleteSubject(@PathVariable (value = "courseId") Long courseId,
                              @PathVariable (value = "subjectId") Long subjectId) {
        Topic Topic = subjectRepository.findByIdCourseIdAndIdSubjectId(courseId,subjectId);
        if(Topic!=null)
        {
            subjectRepository.delete(Topic);
            return ResponseEntity.ok().build();
        }
        else { throw new ResourceNotFoundException("Topic not found with id " + subjectId + " and courseId " + courseId);}
    }*/
}
