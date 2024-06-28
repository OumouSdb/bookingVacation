package com.location.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.location.location.model.Messages;


@Repository
public interface MessagesRepository extends JpaRepository<Messages,Long>{

}
