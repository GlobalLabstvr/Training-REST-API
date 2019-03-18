package com.tvr.training.api.sites;

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
import com.tvr.training.api.topic.TopicRepository;

@RestController
public class SiteController {

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private TopicRepository topicRepository;
    
    @GetMapping("/sites")
    public List<Site> getAllSites( ) {
        return siteRepository.findAll( );
    }
    
    @GetMapping("sites/{siteId}")
    public Optional<Site> getSitelist(
    		@PathVariable (value = "siteId") Long siteId) {
        return siteRepository.findById(siteId);
    }
    
    @GetMapping("/topics/{topicId}/sites")
    public List<Site> geSitesByTopicId(
    		@PathVariable (value = "topicId") Long topicId) {
        return siteRepository.findByTopicId(topicId);
    }
    
       
    @PostMapping("/topics/{topicId}/sites")
    public Site createTopic(
    		@PathVariable (value = "topicId") Long topicId,
    		@Valid @RequestBody Site site) {
        return topicRepository.findById(topicId).map(topic -> {
            site.setTopic(topic);         
            return siteRepository.save(site);
        }).orElseThrow(() -> new ResourceNotFoundException("topicId " + topicId + " not found"));
    }

}
