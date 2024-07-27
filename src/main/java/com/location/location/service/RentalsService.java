package com.location.location.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

	            String imageName = image.getOriginalFilename(); // Récupération du nom de l'image
	            Path path = Paths.get(storageFolder, imageName); // Définition du chemin de l'image
	            if (!Files.exists(path.getParent())) {
	                Files.createDirectories(path.getParent()); // Création des répertoires si nécessaires
	            }
	            Files.write(path, image.getBytes()); // Écriture de l'image dans le système de fichiers

	            return storageFolder + imageName; // Retour de l'URL de l'image
	        } catch (IOException e) {
	            throw new RuntimeException("Échec de l'enregistrement de l'image", e); // Gestion des exceptions d'entrée/sortie
	        }
	    }

	 public ResponseEntity<RentalsDto> saveRentalWithImage(MultipartFile image, RentalsDto iDto) {

		    // Obtenez l'utilisateur actuellement authentifié
		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		    // Suppose que votre entité User a un champ 'email' (ou un champ similaire pour l'identifiant utilisateur)
		    Users user = usersRepository.findByEmail(userDetails.getUsername());
		    if (user == null) {
		        throw new UsernameNotFoundException("User not found with email: " + userDetails.getUsername());
		    }
		    Long userId = user.getId();

		    String imageUrl = saveImage(image); // Sauvegarde de l'image et récupération de l'URL
		    iDto.setPicture(imageUrl); // Mise à jour de l'URL de l'image dans le DTO

		    // Conversion du DTO en entité
		    Rentals img = modelMapper.map(iDto, Rentals.class);

		    // Vérifiez si l'entité existe déjà
		    boolean isNew = img.getId() == null || !rentalsRepository.existsById(img.getId());

		    // Enregistrement de l'entité mise à jour
		    Rentals savedImage = rentalsRepository.save(img);

		    // Conversion de l'entité sauvegardée en DTO
		    RentalsDto savedDto = modelMapper.map(savedImage, RentalsDto.class);

		    // Mettre à jour les dates appropriées
		    if (!isNew) {
		        savedDto.setUpdated_at(new Date());
		    } else {
		        savedDto.setCreated_at(new Date());
		    }

		    // Met à jour le champ owner_id avec l'ID de l'utilisateur authentifié
		    savedDto.setOwner_id(userId);

		    return ResponseEntity.ok(savedDto);
		}


	
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
	

}
