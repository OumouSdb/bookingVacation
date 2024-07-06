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

import com.location.location.DTO.RentalsDto;
import com.location.location.service.RentalsService;



@RestController
@RequestMapping("/api/rentals")
public class RentalsController {

	@Autowired
	RentalsService rentalsService;
	
	@PostMapping("/save")
	public ResponseEntity<RentalsDto> saveRental(@RequestBody RentalsDto r) {
		
		RentalsDto rt = this.rentalsService.save(r);
		return ResponseEntity.status(HttpStatus.CREATED).body(rt);
		
	}
	
	@PutMapping("/update/{id}")
	public RentalsDto updateRental(@PathVariable("id") final Long id, @RequestBody RentalsDto r) {
		Optional<RentalsDto> rent = rentalsService.getrentById(id);
		if(rent.isPresent()) {
			
			return rentalsService.save(r);

		} else {
			return null;
		}
	}
	
	@GetMapping("/")
	public Iterable<RentalsDto> getRentals() {
		return this.rentalsService.getAllRentals();
	}
	
	@GetMapping("/{id}")
	public Optional<RentalsDto> getUserById(@PathVariable("id") long id) {
		return this.rentalsService.getrentById(id);
	}
	
	@DeleteMapping(value="/rentals/{id}")
	public ResponseEntity<Long> deleteById(@PathVariable("id") Long id) {
		this.rentalsService.deleterentById(id);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}

}
