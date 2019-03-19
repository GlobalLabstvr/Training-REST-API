package com.tvr.training.api.playlist;

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
import com.tvr.training.api.topic.Topic;
import com.tvr.training.api.topic.TopicRepository;

@RestController
public class PlaylistController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private PlaylistRepository playlistRepository;
    
    @GetMapping("/playlists")
    public List<Playlist> getAllPlaylists(
    		@RequestParam("name") String name) {
        List<Playlist> list = new ArrayList<Playlist>();
    	if(name!=null && !name.equals("")) {
        	list = playlistRepository.findByNameContaining(name);
        }
    	else {
    		list = playlistRepository.findAll();
    	}
    	return list;
    }
    
    @GetMapping("playlists/{playlistId}")
    public Optional<Playlist> getPlaylist(
    		@PathVariable (value = "playlistId") Long playlistId) {
        return playlistRepository.findById(playlistId);
    }
    
       
    @PostMapping("/playlists/{topicId}")
    public Playlist createTopic(
    		@PathVariable (value = "topicId") Long topicId,
    		@Valid @RequestBody Playlist playlist) {
        return topicRepository.findById(topicId).map(topic -> {
            playlist.setTopic(topic);         
            return playlistRepository.save(playlist);
        }).orElseThrow(() -> new ResourceNotFoundException("topicId " + topicId + " not found"));
    }

    @PutMapping("/playlists")
    public Playlist updatePlaylist(@Valid @RequestBody Playlist playlistRequest) {
    	Long playlistId = playlistRequest.getId();
        return playlistRepository.findById(playlistId).map(playlist -> {
        	playlist.setTopic(playlistRequest.getTopic());
            playlist.setName(playlistRequest.getName());
            playlist.setDescription(playlistRequest.getDescription());
            playlist.setUrl(playlistRequest.getUrl());
          return playlistRepository.save(playlist);
        }).orElseThrow(() -> new ResourceNotFoundException("playlistId " + playlistId + "not found"));
    }

}
