package com.tvr.training.api.subject;

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

import com.tvr.training.api.course.Course;
import com.tvr.training.api.course.CourseRepository;
import com.tvr.training.api.exception.ResourceNotFoundException;

@RestController
public class SubjectController {

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private CourseRepository courseRepository;

	@GetMapping("/subjects")
	public List<Subject> getAllsubjects(
			@RequestParam(value="name",required=false) String name) {
        List<Subject> list = new ArrayList<Subject>();
    	if(name!=null && !name.equals("")) {
        	list = subjectRepository.findByNameContaining(name);
        }
    	else {
    		list = subjectRepository.findAll();
    	}
    	return list;
	}

	@GetMapping("/subjects/{subjectId}")
	public Optional<Subject> getsubjects(@PathVariable(value = "subjectId") Long subjectId) {
		return subjectRepository.findById(subjectId);
	}
	
	@GetMapping("/subjects/course/{courseId}")
	public List<Subject> getsubjectsByCourseId(@PathVariable(value = "courseId") Long courseId) {
		return subjectRepository.findByCourseId(courseId);
	}

	@PostMapping("/subjects/{courseId}")
	public Subject createSubject(
			@PathVariable(value = "courseId") Long courseId,
			@Valid @RequestBody Subject subject) {
		
		return courseRepository.findById(courseId).map(course -> {
			subject.setCourse(course);
			return subjectRepository.save(subject);
		}).orElseThrow(() -> new ResourceNotFoundException("courseId " + courseId + " not found"));
	}

	@PutMapping("/subjects")
	public Subject updateSubject(@Valid @RequestBody Subject subjectRequest) {
		Long subjectId = subjectRequest.getId();
		return subjectRepository.findById(subjectId).map(subject -> {
			return subjectRepository.save(subjectRequest);
		}).orElseThrow(() -> new ResourceNotFoundException("subjectId " + subjectId + " not found"));
	}

}
