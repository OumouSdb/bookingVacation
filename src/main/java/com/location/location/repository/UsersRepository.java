package com.location.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.location.location.model.Users;


@Repository
public interface UsersRepository extends JpaRepository<Users, 	Long>{
 public Users findByEmail(String email);
}
