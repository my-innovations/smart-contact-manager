package com.contactmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contactmanager.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
	
	//@Query("select uFROM User u WHERE u.email = :email") //OK
	@Query("FROM User WHERE email = :email")
	User findByUsername(@Param("email") String email);

}
