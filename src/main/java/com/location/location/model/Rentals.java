package com.location.location.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Rentals {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	 private String name;
     private int surface;
     private int price;
     private String picture;
     private String description;
     private long owner_id;
     private Date created_at;
     private Date updated_at;
     
     

}
