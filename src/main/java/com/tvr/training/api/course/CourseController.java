package com.tvr.training.api.course;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tvr.training.api.exception.ResourceNotFoundException;

@RestController
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/courses")
    public List<Course> getAllcourses( ) {
        return courseRepository.findAll( );
    }
    
    @GetMapping("/courses/{courseId}")
    public Optional<Course> getCourseById(@PathVariable Long courseId) {
        return courseRepository.findById(courseId );
    }

    @PostMapping("/courses")
    public Course createcourse(@Valid @RequestBody Course course) {
        return courseRepository.save(course);
    }

    @PutMapping("/courses/{courseId}")
    public Course updatecourse(@PathVariable Long courseId, @Valid @RequestBody Course courseRequest) {
        return courseRepository.findById(courseId).map(course -> {
            course.setName(courseRequest.getName());
            course.setDescription(courseRequest.getDescription());
            
            return courseRepository.save(course);
        }).orElseThrow(() -> new ResourceNotFoundException("courseId " + courseId + " not found"));
    }


    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<?> deletecourse(@PathVariable Long courseId) {
        return courseRepository.findById(courseId).map(course -> {
            courseRepository.delete(course);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("courseId " + courseId + " not found"));
    }

}
