package com.coderbd.dto;


import com.coderbd.entity.Employee;
import com.coderbd.entity.Pet;
import com.coderbd.enums.EmployeeSkill;
import lombok.Data;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Represents the form that schedule request and response data takes. Does not map
 * to the database directly.
 */
@Data
public class ScheduleDTO {
    private long id;
    private List<Employee> employees;
    private List<Pet> pets;
    private Set<EmployeeSkill> skills;
    private Set<DayOfWeek> daysAvailable;
    private LocalDate scheduleDate;

}
