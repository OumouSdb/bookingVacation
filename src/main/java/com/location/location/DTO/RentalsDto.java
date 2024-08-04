package com.location.location.DTO;

import java.time.Instant;
import java.time.LocalDateTime;
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
	private Instant created_at;
	private Instant updated_at;

}
