package com.coderbd.repository;

import com.coderbd.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * @author Mohammad Rajaul Islam
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
