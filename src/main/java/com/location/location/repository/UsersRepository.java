package com.location.location.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.location.location.model.Users;


@Repository
public interface UsersRepository extends CrudRepository<Users, 	Long>{

}
