package com.location.location.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.location.location.DTO.RentalsDto;
import com.location.location.model.Rentals;
import com.location.location.repository.RentalsRepository;



@Service
public class RentalsService {

	@Autowired
	private RentalsRepository rentalsRepository;
		
	@Autowired
	private ModelMapper modelMapper;
	
	public Iterable<RentalsDto> getAllRentals() {
		
		List<Rentals> Rentals = rentalsRepository.findAll();
		return Rentals.stream().map((rent)->modelMapper.map(rent, RentalsDto.class))
				.collect(Collectors.toList());
	}
	
	public Optional<RentalsDto> getrentById(long id) {
		Optional<Rentals> Rentals = rentalsRepository.findById(id);
		if(Rentals.isPresent()) {
			return Optional.of(modelMapper.map(Rentals, RentalsDto.class));
		}else {
			return Optional.empty();
		}
		
	}
	
	public void deleterentById(long id) {
	 rentalsRepository.deleteById(id);
	}	
	
	public RentalsDto save(RentalsDto rDto) {
		Rentals u = modelMapper.map(rDto, Rentals.class);
		Rentals saveRent = rentalsRepository.save(u);
		RentalsDto saveDto = modelMapper.map(saveRent, RentalsDto.class);
		return saveDto;
	}

}
