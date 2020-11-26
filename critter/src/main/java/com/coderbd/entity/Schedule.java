package com.coderbd.entity;

import com.coderbd.enums.EmployeeSkill;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * @author Mohammad Rajaul Islam
 */
@Data
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonIgnore
    @ManyToMany
    private List<Employee> employees;

    @JsonIgnore
    @ManyToMany
    private List<Pet> pets;
    @Transient
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Scedule_skills", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> skills;
    @Transient
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "employee_days_available", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysAvailable;

    private LocalDate scheduleDate;

}
