package com.coderbd.serviceimpl;

import com.coderbd.dto.CustomerDTO;
import com.coderbd.entity.Customer;
import com.coderbd.repository.CustomerRepository;
import com.coderbd.service.CustomerService;
import com.coderbd.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
@Autowired
private PetService petService;

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        return copyFromCustomerToCustomerDto(this.customerRepository.save(copyFromCustomerDtoToCustomer(customerDTO)));
    }

    @Override
    public Customer update(CustomerDTO customerDTO) {
        if (customerDTO.getId() != 0 && this.customerRepository.existsById(customerDTO.getId())) {
            return this.customerRepository.save(copyFromCustomerDtoToCustomer(customerDTO));
        }
        return null;
    }

    @Override
    public CustomerDTO findById(Long id) {
        return copyFromCustomerToCustomerDto(this.customerRepository.getOne(id));
    }

    @Override
    public boolean isExists(Long id) {
        if (id != null || id != 0) {
            return this.customerRepository.existsById(id);
        }
        return false;
    }

    @Override
    public List<CustomerDTO> findAllCustomers() {
        List<CustomerDTO> dtos = new ArrayList<>();
        this.customerRepository.findAll().forEach((customer) -> {
            CustomerDTO dto=copyFromCustomerToCustomerDto(customer);
            dto.setPets(petService.findAllByOwner(customer.getId()));
            dtos.add(dto);
        });
        return dtos;
    }

     public Customer copyFromCustomerDtoToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    public CustomerDTO copyFromCustomerToCustomerDto(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }
}
