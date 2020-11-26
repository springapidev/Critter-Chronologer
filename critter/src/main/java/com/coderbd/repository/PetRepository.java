package com.coderbd.repository;

import com.coderbd.entity.Customer;
import com.coderbd.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Mohammad Rajaul Islam
 */
@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByCustomer(Customer customer);


}
