package com.tvr.training.api.topic;

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
import com.tvr.training.api.model.PlaylistVO;
import com.tvr.training.api.model.TopicVO;
import com.tvr.training.api.playlist.Playlist;
import com.tvr.training.api.playlist.PlaylistRepository;
import com.tvr.training.api.subject.SubjectRepository;

@RestController
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SubjectRepository subjectRepository;
    
    @Autowired
    private PlaylistRepository playlistRepository;
    
    @GetMapping("/topics")
    public List<Topic> getAllsubjects(
    		@RequestParam("name") String name) {
        List<Topic> list = new ArrayList<Topic>();
    	if(name!=null && !name.equals("")) {
        	list = topicRepository.findByNameContaining(name);
        }
    	else {
    		list = topicRepository.findAll();
    	}
    	return list;
    }
    
    @GetMapping("/topics/{topicId}")
    public Optional<Topic> getTopicById(
    		@PathVariable (value = "topicId") Long topicId) {
       return topicRepository.findById(topicId);
    }
    
    @GetMapping("/topics/subjects/{subjectId}")
    public TopicVO getTopicBySubjectId(
    		@PathVariable (value = "subjectId") Long subjectId) {
        List<Topic> topic = topicRepository.findBySubjectId(subjectId);
        TopicVO topicVO = copy(topic.get(0));
    	List<Playlist>playlists = playlistRepository.findByTopicId(topicVO.getId());
    		playlists.forEach(playlist -> {
    			PlaylistVO playlistVO = copy(playlist);
    			topicVO.getPlaylists().add(playlistVO);
    		});
    	return topicVO;
    }
    
       
    @PostMapping("/topics/{subjectId}")
    public Topic createTopic(
    		@PathVariable (value = "subjectId") Long subjectId,
    		@Valid @RequestBody Topic topic) {
        return subjectRepository.findById(subjectId).map(subject -> {
            topic.setSubject(subject);         
            return topicRepository.save(topic);
        }).orElseThrow(() -> new ResourceNotFoundException("courseId " + subjectId + " not found"));
    }

    @PutMapping("/topics")
    public Topic updateTopic(@Valid @RequestBody Topic topicRequest) {
    	Long topicId = topicRequest.getId();
        return topicRepository.findById(topicId).map(topic -> {
        	topic.setSubject(topicRequest.getSubject());
            topic.setName(topicRequest.getName());
            topic.setDescription(topicRequest.getDescription());
            return topicRepository.save(topic);
        }).orElseThrow(() -> new ResourceNotFoundException("TopicId " + topicId + "not found"));
    }
    
    private TopicVO copy(Topic topic) {
    	TopicVO topicVO = new TopicVO();
    	topicVO.setId(topic.getId());
    	topicVO.setName(topic.getName());
    	topicVO.setDescription(topic.getDescription());
    	return topicVO;
    }
    
    private PlaylistVO copy(Playlist playlist) {
    	PlaylistVO playlistVO = new PlaylistVO();
    	playlistVO.setId(playlist.getId());
    	playlistVO.setName(playlist.getName());
    	playlistVO.setDescription(playlist.getDescription());
    	playlistVO.setUrl(playlist.getUrl());
    	return playlistVO;
    }

}
