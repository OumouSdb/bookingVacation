package com.location.location.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Messages {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;
	private String message;
	@ManyToOne
	@JoinColumn(name = "rentals_id", nullable = false)
	private Rentals rentalId;

	@ManyToOne
	@JoinColumn(name = "users_id", nullable = false)
	private Users userId;
}
