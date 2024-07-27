package com.location.location.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class RentalsDto {
	

	private Long id;
	 private String name;
     private int surface;
     private int price;
     private String picture;
     private String description;
     private long owner_id;
     private Date created_at;
     private Date updated_at;
     
	

}
