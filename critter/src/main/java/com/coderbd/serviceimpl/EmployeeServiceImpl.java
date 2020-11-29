package com.coderbd.serviceimpl;

import com.coderbd.dto.EmployeeDTO;
import com.coderbd.dto.EmployeeRequestDTO;
import com.coderbd.entity.Employee;
import com.coderbd.enums.EmployeeSkill;
import com.coderbd.repository.EmployeeRepository;
import com.coderbd.service.EmployeeService;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        return copyFromEmployeeToEmployeeDto(this.employeeRepository.save(copyFromEmployeeDtoToEmployee(employeeDTO)));
    }

    @Override
    public EmployeeDTO update(Long employeeId, Set<DayOfWeek> daysAvailable) {
        if (employeeId != null && employeeId != 0 && this.employeeRepository.existsById(employeeId)) {
            Employee employee = copyFromEmployeeDtoToEmployee(findById(employeeId));
            employee.setDaysAvailable(daysAvailable);
            return copyFromEmployeeToEmployeeDto(this.employeeRepository.save(employee));
        }
        return null;
    }

    @Override
    public EmployeeDTO findById(Long id) {
        return copyFromEmployeeToEmployeeDto(this.employeeRepository.getOne(id));
    }

    @Override
    public boolean isExists(Long id) {
        if (id != null || id != 0) {
            return this.employeeRepository.existsById(id);
        }
        return false;
    }

    @Override
    public List<EmployeeDTO> findAllEmployeesAvailabilty() {
        List<EmployeeDTO> dtos = new ArrayList<>();
        for (Employee employee : employeeRepository.findAll()) {
            dtos.add(copyFromEmployeeToEmployeeDto(employee));
        }
        return dtos;
    }

    @Override
    public Employee copyFromEmployeeDtoToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        return employee;
    }

    @Override
    public List<EmployeeDTO> getEmployeesForService(EmployeeRequestDTO employeeDTO) {
        List<EmployeeDTO> employeeDTOS = findAllEmployeesAvailabilty();
        List<EmployeeDTO> dtos = new ArrayList<>();
        for (EmployeeDTO dto : employeeDTOS) {
            if (dto.getSkills().containsAll(employeeDTO.getSkills())) {
                dtos.add(dto);
            }
        }
        return dtos;
    }


    @Override
    public EmployeeDTO copyFromEmployeeToEmployeeDto(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        return employeeDTO;
    }


}
