package com.tvr.training.api.document;

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
public class DocumentController {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private TopicRepository topicRepository;
    
    @GetMapping("/documents")
    public List<Document> getAllDocuments(
    		@RequestParam("name") String name) {
        List<Document> list = new ArrayList<Document>();
    	if(name!=null && !name.equals("")) {
        	list = documentRepository.findByNameContaining(name);
        }
    	else {
    		list = documentRepository.findAll();
    	}
    	return list;
    }
    
    @GetMapping("documents/{documentId}")
    public Optional<Document> getDocumentlist(
    		@PathVariable (value = "documentId") Long documentId) {
        return documentRepository.findById(documentId);
    }
    
  
    @PostMapping("/documents/{topicId}")
    public Document createTopic(
    		@PathVariable (value = "topicId") Long topicId,
    		@Valid @RequestBody Document document) {
        return topicRepository.findById(topicId).map(topic -> {
            document.setTopic(topic);         
            return documentRepository.save(document);
        }).orElseThrow(() -> new ResourceNotFoundException("topicId " + topicId + " not found"));
    }
    @PutMapping("/documents")
    public Document updateDocument(@Valid @RequestBody Document documentRequest) {
    	Long documentId = documentRequest.getId();
        return documentRepository.findById(documentId).map(document -> {
        	document.setTopic(documentRequest.getTopic());
            document.setName(documentRequest.getName());
            document.setDescription(documentRequest.getDescription());
            return documentRepository.save(document);
        }).orElseThrow(() -> new ResourceNotFoundException("documentId " + documentId + "not found"));
    }

}
