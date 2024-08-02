package com.location.location.controller;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.location.location.DTO.MessagesDto;
import com.location.location.model.Messages;
import com.location.location.service.MessagesService;


@RestController
@RequestMapping("/api/messages")
public class MessagesController {

	@Autowired
	private MessagesService messagesService;
	
	@PostMapping
	public ResponseEntity<Messages> saveMessage(@RequestBody MessagesDto messageDto) {
	    Messages savedMessage = messagesService.save(messageDto.getRental_id(), messageDto.getUser_id(), messageDto.getMessage());
	    return ResponseEntity.ok(savedMessage);
	}

	
//	@PutMapping(value="/{id}")
//	public MessagesDto updateMessage(@PathVariable("id") final long id, @RequestBody MessagesDto m) {
//		Optional<MessagesDto> message = Optional.empty();
//		if(message.isPresent()) {
//			
//			return messagesService.save(m);
//
//		} else {
//			return null;
//		}
//	}
	
	@GetMapping(value="")
	public Iterable<MessagesDto> getMessages() {
		return this.messagesService.getAllMessages();
	}
	
	@GetMapping(value="/{id}")
	public Optional<MessagesDto> getMessageById(@PathVariable("id") long id) {
		return this.messagesService.getMessageById(id);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Long> deleteById(@PathVariable("id") long id) {
		this.messagesService.deleteMessageById(id);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}
}
