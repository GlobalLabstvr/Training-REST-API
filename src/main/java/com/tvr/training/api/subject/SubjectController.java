package com.tvr.training.api.subject;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tvr.training.api.course.CourseRepository;
import com.tvr.training.api.exception.ResourceNotFoundException;

@RestController
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private CourseRepository courseRepository;
    
    @GetMapping("/subjects")
    public List<Subject> getAllsubjects( ) {
        return subjectRepository.findAll( );
    }
    
    @GetMapping("subjects/{subjectId}")
    public Optional<Subject> getsubjects(
    		@PathVariable (value = "subjectId") Long subjectId) {
        return subjectRepository.findById(subjectId);
    }
    
    @GetMapping("/courses/{courseId}/subjects")
    public List<Subject> getSubjectsByCourseId(
    		@PathVariable (value = "courseId") Long courseId) {
        return subjectRepository.findByCourseId(courseId);
    }
    
       
    @PostMapping("/courses/{courseId}/subjects")
    public Subject createSubject(
    		@PathVariable (value = "courseId") Long courseId,
    		@Valid @RequestBody Subject subject) {
        return courseRepository.findById(courseId).map(course -> {
            subject.setCourse(course);         
            return subjectRepository.save(subject);
        }).orElseThrow(() -> new ResourceNotFoundException("courseId " + courseId + " not found"));
    }

  /*  @PutMapping("/courses/{courseId}/subjects/{subjectId}")
    public Subject updateSubject(@PathVariable (value = "courseId") Long courseId,
                                 @PathVariable (value = "subjectId") Long subjectId,
                                 @Valid @RequestBody Subject subjectRequest) {
        if(!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("courseId " + courseId + " not found");
        }

        return subjectRepository.findById(subjectId).map(subject -> {
            subject.setName(subjectRequest.getName());
            subject.setDescription(subjectRequest.getDescription());
            return subjectRepository.save(subject);
        }).orElseThrow(() -> new ResourceNotFoundException("SubjectId " + subjectId + "not found"));
    }
*/
   /* @DeleteMapping("/courses/{courseId}/subjects/{subjectId}")
    public ResponseEntity<Subject> deleteSubject(@PathVariable (value = "courseId") Long courseId,
                              @PathVariable (value = "subjectId") Long subjectId) {
        Subject subject = subjectRepository.findByIdCourseIdAndIdSubjectId(courseId,subjectId);
        if(subject!=null)
        {
            subjectRepository.delete(subject);
            return ResponseEntity.ok().build();
        }
        else { throw new ResourceNotFoundException("Subject not found with id " + subjectId + " and courseId " + courseId);}
    }*/
}
