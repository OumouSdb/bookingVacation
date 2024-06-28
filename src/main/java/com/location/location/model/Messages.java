package com.location.location.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Messages {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long Id;
	 	private String message;
	    private long user_id;
	    private long rental_id;
		public long getId() {
			return Id;
		}
		public void setId(long id) {
			Id = id;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public long getUser_id() {
			return user_id;
		}
		public void setUser_id(long user_id) {
			this.user_id = user_id;
		}
		public long getRental_id() {
			return rental_id;
		}
		public void setRental_id(long rental_id) {
			this.rental_id = rental_id;
		}
	    
	    

}
