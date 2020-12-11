package com.coderbd.service;

import com.coderbd.dto.CustomerDTO;
import com.coderbd.entity.Customer;
import org.springframework.beans.BeanUtils;


import java.util.List;

public interface CustomerService {
    CustomerDTO save(CustomerDTO customerDTO);
    Customer update(CustomerDTO customerDTO);
    CustomerDTO findById(Long id);
    boolean isExists(Long id);
    List<CustomerDTO> findAllCustomers();
    Customer copyFromCustomerDtoToCustomer(CustomerDTO customerDTO);

    CustomerDTO copyFromCustomerToCustomerDto(Customer customer);
}
