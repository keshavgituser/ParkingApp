package com.practice.parkapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.practice.parkapp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   public List<User> findByUserType(USERTYPE userType);
   
   public User findByUserName(String userName);
	
	
	

}
