package com.springspartans.shopkart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springspartans.shopkart.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>{
	 @Query(value="SELECT * FROM admin WHERE email=?", nativeQuery=true)
	 Optional<Admin> findByEmail(String email);
}