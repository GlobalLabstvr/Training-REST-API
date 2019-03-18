package com.tvr.training.api.course;

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

@RestController
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/courses")
    public List<Course> getAllcourses(
    		 @RequestParam("name") String name) {
        List<Course> list = new ArrayList<Course>();
    	if(name!=null && !name.equals("")) {
        	list = courseRepository.findByNameContaining(name);
        }
    	else {
    		list = courseRepository.findAll();
    	}
    	return list;
    }
    
    @GetMapping("/courses/{courseId}")
    public Optional<Course> getCourseById(@PathVariable Long courseId) {
        return courseRepository.findById(courseId );
    }

    @PostMapping("/courses")
    public Course createcourse(@Valid @RequestBody Course course) {
        return courseRepository.save(course);
    }

    @PutMapping("/courses")
    public Course updatecourse(@Valid @RequestBody Course courseRequest) {
        Long courseId = courseRequest.getId();
    	return courseRepository.findById(courseId).map(course -> {
            course.setName(courseRequest.getName());
            course.setDescription(courseRequest.getDescription());
            return courseRepository.save(course);
        }).orElseThrow(() -> new ResourceNotFoundException("courseId " + courseId + " not found"));
    }

}
