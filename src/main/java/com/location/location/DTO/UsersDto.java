package com.location.location.DTO;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


public class UsersDto {
	

	 private long id;
	  private String name;
	   private String email;
	    private String password;
	    private String role;
	    private Date created_at;
	    private Date updated_at;
	
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public UsersDto(long id, String name, String email, Date created_at, Date updated_at) {
			super();
			this.id = id;
			this.name = name;
			this.email = email;
			this.created_at = created_at;
			this.updated_at = updated_at;
		}
	
	

	    
}
