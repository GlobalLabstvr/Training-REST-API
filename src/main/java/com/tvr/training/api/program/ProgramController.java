package com.tvr.training.api.program;


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
import com.tvr.training.api.playlist.Playlist;
import com.tvr.training.api.topic.TopicRepository;

@RestController
public class ProgramController {

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private TopicRepository topicRepository;
    
    @GetMapping("/programs")
    public List<Program> getAllprograms( ) {
        return programRepository.findAll( );
    }
    
    @GetMapping("programs/{programId}")
    public Optional<Program> getprogramlist(
    		@PathVariable (value = "programId") Long programId) {
        return programRepository.findById(programId);
    }
    
    @GetMapping("/topics/{topicId}/programs")
    public List<Program> geprogramsByTopicId(
    		@PathVariable (value = "topicId") Long topicId) {
        return programRepository.findByTopicId(topicId);
    }
    
       
    @PostMapping("/topics/{topicId}/programs")
    public Program createTopic(
    		@PathVariable (value = "topicId") Long topicId,
    		@Valid @RequestBody Program program) {
        return topicRepository.findById(topicId).map(topic -> {
            program.setTopic(topic);         
            return programRepository.save(program);
        }).orElseThrow(() -> new ResourceNotFoundException("topicId " + topicId + " not found"));
    }


    @PutMapping("/topics/{topicId}/programs/{programsId}")
public Program updateProgram(@PathVariable (value = "topicId") Long topicId,
                             @PathVariable (value = "programId") Long programId,
                             @Valid @RequestBody Program programRequest) {
    if(!topicRepository.existsById(topicId)) {
        throw new ResourceNotFoundException("topicId " + topicId + " not found");
    }

    return programRepository.findById(programId).map(program -> {
        program.setName(programRequest.getName());
        program.setDescription(programRequest.getDescription());
        return programRepository.save(program);
    }).orElseThrow(() -> new ResourceNotFoundException("ProgramId " + programId + "not found"));
}
}


