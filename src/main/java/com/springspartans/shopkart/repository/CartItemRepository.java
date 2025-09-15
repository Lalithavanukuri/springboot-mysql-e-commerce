package com.springspartans.shopkart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springspartans.shopkart.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	@Query(value="SELECT * FROM cart_item WHERE cust_id=?", nativeQuery=true)
	List<CartItem> findByCustId(int custId);
}
