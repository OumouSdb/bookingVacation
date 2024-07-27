package com.location.location.controller;

import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.location.location.DTO.RentalsDto;
import com.location.location.service.RentalsService;


@RestController
@RequestMapping("/api/rentals")
public class RentalsController {

	@Autowired
	RentalsService rentalsService;
	
	@Value("${app.storagefolder}")
	private String storageFolder;
	
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RentalsDto> createImage(@RequestParam("pic") MultipartFile pic,
                                                @ModelAttribute RentalsDto dto) {
        return rentalsService.saveRentalWithImage(pic, dto);
    }
	

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RentalsDto> updateImage(@RequestParam("pic") MultipartFile pic,
                                                @ModelAttribute RentalsDto dto) {
        return rentalsService.saveRentalWithImage(pic, dto);
    }
	
	@GetMapping(value="", consumes="application/json", produces="application/json")
	public Iterable<RentalsDto> getRentals() {
		return this.rentalsService.getAllRentals();
	}
	
	@GetMapping(value = "/{id}", consumes="application/json", produces="application/json")
	public Optional<RentalsDto> getUserById(@PathVariable("id") long id) {
		return this.rentalsService.getrentById(id);
	}
	
	@DeleteMapping(value="/{id}", consumes="application/json", produces="application/json")
	public ResponseEntity<Long> deleteById(@PathVariable("id") Long id) {
		this.rentalsService.deleterentById(id);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}

}
