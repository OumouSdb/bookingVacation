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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.location.location.DTO.MessagesDto;
import com.location.location.model.Messages;
import com.location.location.service.MessagesService;


@RestController
@RequestMapping("/api/messages")
public class MessagesController {

	@Autowired
	private MessagesService messagesService;
	
	@PostMapping(consumes="application/json", produces="application/json")
	public ResponseEntity<MessagesDto> saveMessage(@RequestBody MessagesDto m) {
		
		MessagesDto msg = this.messagesService.save(m);
		return ResponseEntity.status(HttpStatus.CREATED).body(msg);
		
	}
	
	@PutMapping("/{id}")
	public MessagesDto updateMessage(@PathVariable("id") final Long id, @RequestBody MessagesDto m) {
		Optional<Messages> message = Optional.empty();
		if(message.isPresent()) {
			
			return messagesService.save(m);

		} else {
			return null;
		}
	}
	
	@GetMapping("")
	public Iterable<MessagesDto> getMessages() {
		return this.messagesService.getAllMessages();
	}
	
	@GetMapping("/{id}")
	public Optional<MessagesDto> getMessageById(@PathVariable("id") long id) {
		return this.messagesService.getMessageById(id);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Long> deleteById(@PathVariable("id") long id) {
		this.messagesService.deleteMessageById(id);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}
}
