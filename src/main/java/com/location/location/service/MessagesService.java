package com.location.location.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.location.location.model.Messages;
import com.location.location.repository.MessagesRepository;


@Service
public class MessagesService {

	
	@Autowired
	private MessagesRepository messagesRepository;
	
	public Iterable<Messages> getAllMessages() {
		return messagesRepository.findAll();
	}
	
	public Optional<Messages> getMessageById(long id) {
		return messagesRepository.findById(id);
	}
	
	public void deleteMessageById(long id) {
		messagesRepository.deleteById(id);
	}	
	
	public Messages save(Messages message) {
		return messagesRepository.save(message);
	}
}
