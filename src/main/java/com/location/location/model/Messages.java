package com.location.location.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Messages {
	
	 	private String message;
	    private long user_id;
	    private long rental_id;

}
