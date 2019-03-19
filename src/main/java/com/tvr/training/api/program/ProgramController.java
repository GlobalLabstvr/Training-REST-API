package com.tvr.training.api.program;


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
import com.tvr.training.api.playlist.Playlist;
import com.tvr.training.api.topic.TopicRepository;

@RestController
public class ProgramController {

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private TopicRepository topicRepository;
    
    @GetMapping("/programs")
    public List<Program> getAllPrograms(
    		@RequestParam("name") String name) {
        List<Program> list = new ArrayList<Program>();
    	if(name!=null && !name.equals("")) {
        	list = programRepository.findByNameContaining(name);
        }
    	else {
    		list = programRepository.findAll();
    	}
    	return list;
    }
    
    @GetMapping("programs/{programId}")
    public Optional<Program> getprogramlist(
    		@PathVariable (value = "programId") Long programId) {
        return programRepository.findById(programId);
    }
    
          
    @PostMapping("/programs/{topicId}")
    public Program createTopic(
    		@PathVariable (value = "topicId") Long topicId,
    		@Valid @RequestBody Program program) {
        return topicRepository.findById(topicId).map(topic -> {
            program.setTopic(topic);         
            return programRepository.save(program);
        }).orElseThrow(() -> new ResourceNotFoundException("topicId " + topicId + " not found"));
    }


    @PutMapping("/programs")
    public Program updateProgram(@Valid @RequestBody Program programRequest) {
    	Long programId = programRequest.getId();
        return programRepository.findById(programId).map(program -> {
        	program.setTopic(programRequest.getTopic());
            program.setName(programRequest.getName());
            program.setDescription(programRequest.getDescription());
            program.setUrl(programRequest.getUrl());
          return programRepository.save(program);
        }).orElseThrow(() -> new ResourceNotFoundException("programId " + programId + "not found"));
    }}


