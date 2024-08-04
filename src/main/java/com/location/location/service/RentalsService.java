package com.location.location.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.location.location.DTO.RentalsDto;
import com.location.location.DTO.UsersDto;
import com.location.location.model.Rentals;
import com.location.location.model.Users;
import com.location.location.repository.RentalsRepository;
import com.location.location.repository.UsersRepository;

@Service
public class RentalsService {

	@Autowired
	private RentalsRepository rentalsRepository;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Value("${app.storagefolder}")
	private String storageFolder;

	private String saveImage(MultipartFile image) {
		try {
			if (image.isEmpty()) {
				throw new RuntimeException("Le fichier image est vide");
			}

			String imageName = image.getOriginalFilename();
			Path path = Paths.get(storageFolder, imageName);

			System.out.println("Saving file to: " + path.toString());

			if (!Files.exists(path.getParent())) {
				Files.createDirectories(path.getParent());
			}
			Files.write(path, image.getBytes());

			System.out.println("File saved successfully!");

			// Retourne l'URL où l'image peut être accédée
			String imageUrl = "http://localhost:3001/" + imageName;
			return imageUrl;
		} catch (IOException e) {
			e.printStackTrace(); // Imprime la trace de la pile pour diagnostiquer l'erreur
			throw new RuntimeException("Échec de l'enregistrement de l'image", e);
		}
	}

	public ResponseEntity<RentalsDto> saveRentalWithImage(MultipartFile image, RentalsDto iDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Users user = usersRepository.findByEmail(userDetails.getUsername());
		if (user == null) {
			throw new UsernameNotFoundException("User not found with email: " + userDetails.getUsername());
		}
		Long userId = user.getId();
		String imageUrl = saveImage(image);
		iDto.setPicture(imageUrl);
		Rentals img = modelMapper.map(iDto, Rentals.class);
		img.setOwnerId(user);
		Rentals savedImage = rentalsRepository.save(img);

		RentalsDto savedDto = modelMapper.map(savedImage, RentalsDto.class);
		savedDto.setOwnerId(user.getId());
		return ResponseEntity.ok(savedDto);
	}

	public Iterable<RentalsDto> getAllRentals() {

		List<Rentals> Rentals = rentalsRepository.findAll();
		return Rentals.stream().map((rent) -> modelMapper.map(rent, RentalsDto.class)).collect(Collectors.toList());
	}

	public Optional<RentalsDto> getrentById(long id) {
		Optional<Rentals> Rentals = rentalsRepository.findById(id);
		if (Rentals.isPresent()) {
			return Optional.of(modelMapper.map(Rentals, RentalsDto.class));
		} else {
			return Optional.empty();
		}

	}

	public void deleterentById(long id) {
		rentalsRepository.deleteById(id);
	}

}
