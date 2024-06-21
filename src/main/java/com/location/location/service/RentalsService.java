package com.location.location.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.location.location.model.Rentals;
import com.location.location.repository.RentalsRepository;


@Service
public class RentalsService {

	@Autowired
	private RentalsRepository rentalsRepository;
	
	public Iterable<Rentals> getAllRentals() {
		return rentalsRepository.findAll();
	}
	
	public Optional<Rentals> getRentalById(long id) {
		return rentalsRepository.findById(id);
	}
	
	public void deleteRentalById(long id) {
		rentalsRepository.deleteById(id);
	}	
	
	public Rentals save(Rentals rent) {
		return rentalsRepository.save(rent);
	}
}
