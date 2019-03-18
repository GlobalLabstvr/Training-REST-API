package com.tvr.training.api.playlist;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tvr.training.api.exception.ResourceNotFoundException;
import com.tvr.training.api.topic.Topic;
import com.tvr.training.api.topic.TopicRepository;

@RestController
public class PlaylistController {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private TopicRepository topicRepository;
    
    @GetMapping("/playlists")
    public List<Playlist> getAllPlaylists( ) {
        return playlistRepository.findAll( );
    }
    
    @GetMapping("playlists/{playlistId}")
    public Optional<Playlist> getPlaylist(
    		@PathVariable (value = "playlistId") Long playlistId) {
        return playlistRepository.findById(playlistId);
    }
    
    @GetMapping("/topics/{topicId}/playlists")
    public List<Playlist> getPlaylistsByTopicId(
    		@PathVariable (value = "topicId") Long topicId) {
        return playlistRepository.findByTopicId(topicId);
    }
    
       
    @PostMapping("/topics/{topicId}/playlists")
    public Playlist createTopic(
    		@PathVariable (value = "topicId") Long topicId,
    		@Valid @RequestBody Playlist playlist) {
        return topicRepository.findById(topicId).map(topic -> {
            playlist.setTopic(topic);         
            return playlistRepository.save(playlist);
        }).orElseThrow(() -> new ResourceNotFoundException("topicId " + topicId + " not found"));
    }
    
    @PutMapping("/topics/{topicId}/playlists/{playlistId}")
    public Playlist updatePlaylist(@PathVariable (value = "topicId") Long topicId,
                                 @PathVariable (value = "playlistId") Long playlistId,
                                 @Valid @RequestBody Playlist playlistRequest) {
        if(!topicRepository.existsById(topicId)) {
            throw new ResourceNotFoundException("topicId " + topicId + " not found");
        }

        return playlistRepository.findById(playlistId).map(playlist -> {
            playlist.setName(playlistRequest.getName());
            playlist.setDescription(playlistRequest.getDescription());
            return playlistRepository.save(playlist);
        }).orElseThrow(() -> new ResourceNotFoundException("PlaylistId " + playlistId + "not found"));
    }
    

}
