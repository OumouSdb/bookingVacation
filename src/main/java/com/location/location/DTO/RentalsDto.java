package com.location.location.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class RentalsDto {
	

	private long id;
	 private String name;
     private int surface;
     private int price;
     private String picture;
     private String description;
     private long ownerId;
     private Date created_at;
     private Date updated_at;
     
	

}
