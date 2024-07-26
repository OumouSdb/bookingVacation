package com.location.location.DTO;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
	

	private long id;
    private String name;
    private String email;
    private String password;
    private String role;
    private Date created_at;
    private Date updated_at;
    private String token;
	
		
	    
}
