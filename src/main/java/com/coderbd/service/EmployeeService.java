package com.coderbd.service;

import com.coderbd.dto.EmployeeDTO;
import com.coderbd.dto.EmployeeRequestDTO;
import com.coderbd.entity.Employee;
import com.coderbd.enums.EmployeeSkill;
import org.springframework.beans.BeanUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface EmployeeService {
    EmployeeDTO save(EmployeeDTO employeeDTO);

    EmployeeDTO update(Long employeeId, Set<DayOfWeek> daysAvailable);

    EmployeeDTO findById(Long id);

    boolean isExists(Long id);

    List<EmployeeDTO> findAllEmployeesAvailabilty();
    EmployeeDTO copyFromEmployeeToEmployeeDto(Employee employee);
    Employee copyFromEmployeeDtoToEmployee(EmployeeDTO employeeDTO);
    List<EmployeeDTO> getEmployeesForService(EmployeeRequestDTO employeeDTO);
}
