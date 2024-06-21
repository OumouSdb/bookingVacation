package com.location.location.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.location.location.model.Messages;


@Repository
public interface MessagesRepository extends CrudRepository<Messages,Long>{

}
