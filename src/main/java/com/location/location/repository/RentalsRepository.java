package com.location.location.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.location.location.model.Rentals;

@Repository
public interface RentalsRepository extends CrudRepository<Rentals, Long>{

}
