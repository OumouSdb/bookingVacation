package com.location.location.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.location.location.DTO.MessagesDto;
import com.location.location.DTO.UsersDto;
import com.location.location.model.Messages;
import com.location.location.model.Rentals;
import com.location.location.model.Users;
import com.location.location.repository.MessagesRepository;
import com.location.location.repository.RentalsRepository;
import com.location.location.repository.UsersRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class MessagesService {

	
	@Autowired
	private MessagesRepository messagesRepository;
	
	@Autowired
	private RentalsRepository rentalsRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
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
	
	public MessagesDto save(Long rentalId, Long userId, String messageContent) {
		
		 Optional<Users> user = usersRepository.findById(userId);
		    Optional<Rentals> rental = rentalsRepository.findById(rentalId);
		    
		    if (user.isPresent() && rental.isPresent()) {
		        Messages message = new Messages();
		        message.setUserId(user.get());
		        message.setRentalId(rental.get());
		        message.setMessage(messageContent);
		         messagesRepository.save(message);
		         return modelMapper.map(message, MessagesDto.class);
		    } else {
		        throw new IllegalArgumentException("Invalid User ID or Rental ID");
		    }
	
	}


}
