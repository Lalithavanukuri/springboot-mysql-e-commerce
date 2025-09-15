package com.springspartans.shopkart.repository;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springspartans.shopkart.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	@Query(value="SELECT * FROM customer WHERE email=?", nativeQuery=true)
    Optional<Customer> findByEmail(String email);
	
    @Query(value="SELECT COUNT(*) FROM customer WHERE signup_date BETWEEN ?1 AND ?2", nativeQuery=true)
    int countBySignup_dateBetween(Timestamp startDate, Timestamp endDate);
    
    @Query(value="SELECT COUNT(*) FROM customer WHERE last_login_date BETWEEN ?1 AND ?2", nativeQuery=true)
    int countByLast_login_dateBetween(Timestamp startDate, Timestamp endDate);
    
}
