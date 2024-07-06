package com.location.location.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.location.location.DTO.MessagesDto;
import com.location.location.model.Messages;
import com.location.location.repository.MessagesRepository;


@Service
public class MessagesService {

	
	@Autowired
	private MessagesRepository messagesRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Iterable<MessagesDto> getAllMessages() {
		
		List<Messages> Messages = messagesRepository.findAll();
		return Messages.stream().map((m)->modelMapper.map(m, MessagesDto.class))
				.collect(Collectors.toList());
	}
	
	public Optional<MessagesDto> getMessageById(long id) {
		Optional<Messages> Messages = messagesRepository.findById(id);
		if(Messages.isPresent()) {
			return Optional.of(modelMapper.map(Messages, MessagesDto.class));
		}else {
			return Optional.empty();
		}
		
	}
	
	public void deleteMessageById(long id) {
	 messagesRepository.deleteById(id);
	}	
	
	public MessagesDto save(MessagesDto mDto) {
		Messages m = modelMapper.map(mDto, Messages.class);
		Messages saveMessage = messagesRepository.save(m);
		MessagesDto saveDto = modelMapper.map(saveMessage, MessagesDto.class);
		return saveDto;
	}
}
