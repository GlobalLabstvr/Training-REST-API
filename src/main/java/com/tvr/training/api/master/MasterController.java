package com.tvr.training.api.master;

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
public class MasterController {

    @Autowired
    private MasterRepository masterRepository;

    @Autowired
    private SlideRepository slideRepository;
    
    @GetMapping("/masters")
    public List<Master> getAllMasters( ) {
        return masterRepository.findAll( );
    }
    
    @GetMapping("masters/{masterId}")
    public Optional<Master> getMasterlist(
    		@PathVariable (value = "masterId") Long masterId) {
        return masterRepository.findById(masterId);
    }
    
    @GetMapping("/slides/{slideId}/masters")
    public List<Master> getMastersBySlideId(
    		@PathVariable (value = "slideId") Long slideId) {
        return masterRepository.findBySlideId(slideId);
    }
    
       
    @PostMapping("/slides/{slideId}/masters")
    public Master createMaster(
    		@PathVariable (value = "slideId") Long slideId,
    		@Valid @RequestBody Master master) {
        return slideRepository.findById(slideId).map(slide -> {
            master.setSlide(slide);         
            return masterRepository.save(master);
        }).orElseThrow(() -> new ResourceNotFoundException("slideId " + slideId + " not found"));
    }

}
