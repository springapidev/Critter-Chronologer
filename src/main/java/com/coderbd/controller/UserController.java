package com.coderbd.controller;

import com.coderbd.dto.CustomerDTO;
import com.coderbd.dto.EmployeeDTO;
import com.coderbd.dto.EmployeeRequestDTO;
import com.coderbd.entity.Customer;
import com.coderbd.entity.Employee;
import com.coderbd.entity.Pet;
import com.coderbd.service.CustomerService;
import com.coderbd.service.EmployeeService;
import com.coderbd.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PetService petService;
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/customer")
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO dto = this.customerService.save(customerDTO);
        return new ResponseEntity<CustomerDTO>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/customer")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return new ResponseEntity<List<CustomerDTO>>(this.customerService.findAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("/customer/pet/{petId}")
    public ResponseEntity<CustomerDTO> getOwnerByPet(@PathVariable long petId) {
        if (this.petService.isExists(petId)) {
            return new ResponseEntity<CustomerDTO>(this.petService.findByPetId(petId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDTO> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO dto = this.employeeService.save(employeeDTO);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }
    @GetMapping("/employee")
    public ResponseEntity<List<EmployeeDTO>> getEmployee() {

        List<EmployeeDTO> dtos = this.employeeService.findAllEmployeesAvailabilty();
        return new ResponseEntity<List<EmployeeDTO>>(dtos, HttpStatus.OK);

    }
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable long employeeId) {

            EmployeeDTO dto = this.employeeService.findById(employeeId);
            return new ResponseEntity<EmployeeDTO>(dto, HttpStatus.OK);

    }

    @PutMapping("/employee/{employeeId}")
    public ResponseEntity<EmployeeDTO> setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        if (employeeService.isExists(employeeId) && daysAvailable.size() > 0) {
            this.employeeService.update(employeeId, daysAvailable);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<EmployeeDTO> employees = employeeService.getEmployeesForService(employeeDTO);
        return employees;
    }
//    @GetMapping("/employee/availability")
//    public List<EmployeeDTO> findEmployeesForServices() {//@RequestBody EmployeeRequestDTO employeeDTO
//        return this.employeeService.findAllEmployeesAvailabilty();
//    }



    private EmployeeDTO getEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        return employeeDTO;
    }
}
