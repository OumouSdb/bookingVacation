package com.location.location.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.location.location.model.Users;


@Repository
public interface UsersRepository extends JpaRepository<Users, 	Long>{
@Query("SELECT u FROM Users u WHERE u.email = :email")
 public Users findByEmail(@Param("email") String email);
}
