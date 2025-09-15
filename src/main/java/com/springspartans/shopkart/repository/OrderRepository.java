package com.springspartans.shopkart.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springspartans.shopkart.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	@Query(value = "SELECT * FROM orders ORDER BY id DESC", nativeQuery = true)
	List<Order> findAllReverse();
	
	@Query(value = "SELECT * FROM orders WHERE cust_id = ?", nativeQuery = true)
	List<Order> findByCustId(int custId);
	
	@Query(value = "SELECT * FROM orders WHERE cust_id = ? ORDER BY id DESC", nativeQuery = true)
	List<Order> findByCustIdReverse(int custId);

	List<Order> findByStatus(String status);
	
	@Query(value = "SELECT * FROM orders WHERE status = ? ORDER BY id DESC", nativeQuery = true)
	List<Order> findByStatusReverse(String status);
	
	@Query(value = "SELECT * FROM orders WHERE status = ?1 AND cust_id = ?2", nativeQuery = true)
	List<Order> findByStatusCustId(String status, int custId);
	
	@Query(value = "SELECT * FROM orders WHERE status = ?1 AND cust_id = ?2 ORDER BY id DESC", nativeQuery = true)
	List<Order> findByStatusCustIdReverse(String status, int custId);

	@Query(value = "SELECT COUNT(*) FROM orders WHERE status = ?", nativeQuery = true)
	int countByStatus(String status);
	
	@Query(value = "SELECT COUNT(*) FROM orders WHERE status = ?1 AND cust_id = ?2", nativeQuery = true)
	int countByStatusCustId(String status, int custId);

	@Query(value = "SELECT COUNT(*) FROM orders WHERE DATE(order_date) = DATE(?)", nativeQuery = true)
	int countByOrderDate(Timestamp orderDate);

	@Query(value = "SELECT COUNT(DISTINCT cust_id) FROM orders WHERE DATE(order_date) = DATE(?)", nativeQuery = true)
	int countCustomersWhoPlacedOrderOnDate(Timestamp orderDate);
	
	@Query(value = "SELECT IFNULL(SUM(total_amount), 0) FROM orders "
			+ "WHERE status != 'Cancelled' "
			+ "AND order_date BETWEEN ?1 AND ?2", nativeQuery = true)
	double getSalesForDuration(Timestamp start, Timestamp end);

	@Query(value = "SELECT prod_id, SUM(quantity) FROM orders "
			+ "WHERE status != 'Cancelled' GROUP BY prod_id "
			+ "ORDER BY SUM(quantity) DESC LIMIT ?", nativeQuery = true)
	List<Integer[]> getTopSellingProducts(int n);
	
}
