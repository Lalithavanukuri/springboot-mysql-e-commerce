package com.springspartans.shopkart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springspartans.shopkart.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    
    @Query(value = "SELECT * FROM product WHERE name LIKE CONCAT(?1, '%')", nativeQuery = true)
    List<Product> findByStartName(String name);
    
    @Query(value = "SELECT * FROM product WHERE category = ?1", nativeQuery = true)
    List<Product> findByCategory(String category);
    
    @Query(value = "SELECT DISTINCT(category) FROM product", nativeQuery=true)
    List<String> findAllCategories();
}
