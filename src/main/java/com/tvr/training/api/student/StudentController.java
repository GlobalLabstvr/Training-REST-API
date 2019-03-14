package com.tvr.training.api.student;

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
import com.tvr.training.api.slide.SlideRepository;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SlideRepository slideRepository;
    
    @GetMapping("/students")
    public List<Student> getAllStudents( ) {
        return studentRepository.findAll( );
    }
    
    @GetMapping("students/{studentId}")
    public Optional<Student> getStudentlist(
    		@PathVariable (value = "studentId") Long studentId) {
        return studentRepository.findById(studentId);
    }
    
    @GetMapping("/slides/{slideId}/students")
    public List<Student> getStudentsBySlideId(
    		@PathVariable (value = "slideId") Long slideId) {
        return studentRepository.findBySlideId(slideId);
    }
    
       
    @PostMapping("/slides/{slideId}/students")
    public Student createMaster(
    		@PathVariable (value = "slideId") Long slideId,
    		@Valid @RequestBody Student student) {
        return slideRepository.findById(slideId).map(slide -> {
            student.setSlide(slide);         
            return studentRepository.save(student);
        }).orElseThrow(() -> new ResourceNotFoundException("slideId " + slideId + " not found"));
    }

}
