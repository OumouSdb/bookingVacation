package com.location.location.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.location.location.DTO.RentalsDto;
import com.location.location.service.RentalsService;




@RestController
@RequestMapping("/api/rentals")
public class RentalsController {

	@Autowired
	RentalsService rentalsService;
	
	@Value("${app.storagefolder}")
	private String storageFolder;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
    @PostMapping("")
    public ResponseEntity<RentalsDto> createImage(@RequestParam("image") MultipartFile picture,
                                                @ModelAttribute RentalsDto imageDto) {
        return rentalsService.saveRentalWithImage(picture, imageDto);
    }
	
	@GetMapping("")
	public Iterable<RentalsDto> getRentals() {
		return this.rentalsService.getAllRentals();
	}
	
	@GetMapping("/{id}")
	public Optional<RentalsDto> getUserById(@PathVariable("id") long id) {
		return this.rentalsService.getrentById(id);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Long> deleteById(@PathVariable("id") Long id) {
		this.rentalsService.deleterentById(id);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}

}
