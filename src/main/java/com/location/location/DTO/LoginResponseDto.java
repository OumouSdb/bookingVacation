package com.location.location.DTO;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

	private Long id;
	private String name;
	private String email;
	private Date createdAt;
	private Date updatedAt;
	private String token;

	public LoginResponseDto(Long id, String name, String email, Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

}