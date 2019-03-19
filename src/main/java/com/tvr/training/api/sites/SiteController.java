package com.tvr.training.api.sites;

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
import com.tvr.training.api.program.Program;
import com.tvr.training.api.topic.TopicRepository;

@RestController
public class SiteController {

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private TopicRepository topicRepository;
    
    @GetMapping("/sites")
    public List<Site> getAllSites(
    		@RequestParam("name") String name) {
        List<Site> list = new ArrayList<Site>();
    	if(name!=null && !name.equals("")) {
        	list = siteRepository.findByNameContaining(name);
        }
    	else {
    		list = siteRepository.findAll();
    	}
    	return list;
    }
    
    @GetMapping("sites/{siteId}")
    public Optional<Site> getSitelist(
    		@PathVariable (value = "siteId") Long siteId) {
        return siteRepository.findById(siteId);
    }
    
  
    @PostMapping("/sites/{topicId}")
    public Site createTopic(
    		@PathVariable (value = "topicId") Long topicId,
    		@Valid @RequestBody Site site) {
        return topicRepository.findById(topicId).map(topic -> {
            site.setTopic(topic);         
            return siteRepository.save(site);
        }).orElseThrow(() -> new ResourceNotFoundException("topicId " + topicId + " not found"));
    }
    @PutMapping("/sites")
    public Site updateSite(@Valid @RequestBody Site siteRequest) {
    	Long siteId = siteRequest.getId();
        return siteRepository.findById(siteId).map(site -> {
        	site.setTopic(siteRequest.getTopic());
            site.setName(siteRequest.getName());
            site.setDescription(siteRequest.getDescription());
            site.setUrl(siteRequest.getUrl());
          return siteRepository.save(site);
        }).orElseThrow(() -> new ResourceNotFoundException("siteId " + siteId + "not found"));
    }

}
