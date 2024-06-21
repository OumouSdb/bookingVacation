package com.location.location.model;

import java.util.Date;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Users {
	
	 private long id;
	  private String name;
	   private String email;
	    private Date created_at;
	    private Date updated_at;

}
