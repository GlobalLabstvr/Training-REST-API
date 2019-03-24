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
import com.tvr.training.api.model.CourseVO;
import com.tvr.training.api.model.SubjectVO;
import com.tvr.training.api.subject.Subject;
import com.tvr.training.api.subject.SubjectRepository;

@RestController
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    
    @GetMapping("/courses")
    public List<CourseVO> getAllcourses(
    		 @RequestParam(value="name",required=false) String name) {
        List<Course> list = new ArrayList<Course>();
    	if(name!=null && !name.equals("")) {
        	list = courseRepository.findByNameContaining(name);
        }
    	else {
    		list = courseRepository.findAll();
    	}
    	List<CourseVO> courseVOList = new ArrayList<>();
    	
    	list.forEach(course -> {
    		CourseVO courseVO = copy(course);
    		List<Subject>subjects = subjectRepository.findByCourseId(course.getId());
    		subjects.forEach(subject -> {
    			SubjectVO subjectVO = copy(subject);
    			courseVO.getSubjects().add(subjectVO);
    		});
    		courseVOList.add(courseVO);
    	});
    	
    	return courseVOList;
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
    
    private CourseVO copy(Course course) {
    	CourseVO courseVO = new CourseVO();
    	courseVO.setId(course.getId());
    	courseVO.setName(course.getName());
    	courseVO.setDescription(course.getDescription());
    	return courseVO;
    }
    
    private SubjectVO copy(Subject subject) {
    	SubjectVO subjectVO = new SubjectVO();
    	subjectVO.setId(subject.getId());
    	subjectVO.setName(subject.getName());
    	subjectVO.setDescription(subject.getDescription());
    	return subjectVO;
    }

}
