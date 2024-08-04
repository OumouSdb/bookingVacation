package com.location.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.location.location.DTO.RentalsDto;
import com.location.location.model.Rentals;

@Repository
public interface RentalsRepository extends JpaRepository<Rentals, Long> {

	void save(RentalsDto rental);

}
