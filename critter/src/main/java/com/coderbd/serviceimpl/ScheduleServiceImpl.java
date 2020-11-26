package com.coderbd.serviceimpl;

import com.coderbd.dto.EmployeeDTO;
import com.coderbd.dto.PetDTO;
import com.coderbd.dto.ScheduleDTO;
import com.coderbd.entity.Employee;
import com.coderbd.entity.Pet;
import com.coderbd.entity.Schedule;
import com.coderbd.enums.EmployeeSkill;
import com.coderbd.repository.ScheduleRepository;
import com.coderbd.service.EmployeeService;
import com.coderbd.service.PetService;
import com.coderbd.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private EmployeeService employeeService;

    @Override
    public void createSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setEmployees(scheduleDTO.getEmployees());
        schedule.setPets(scheduleDTO.getPets());
        schedule.setScheduleDate(scheduleDTO.getScheduleDate());
        this.scheduleRepository.save(schedule);
    }

    @Override
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> dtos = new ArrayList<>();
        Set<EmployeeSkill> skills = new HashSet<>();
        Set<DayOfWeek> avDays = new HashSet<>();
        this.scheduleRepository.findAll().forEach((schedule) -> {
            ScheduleDTO dto = new ScheduleDTO();
            dto.setId(schedule.getId());
            dto.setEmployees(schedule.getEmployees());
            dto.setPets(schedule.getPets());

            schedule.getEmployees().forEach((employee) -> {
                skills.addAll(employee.getSkills());
                avDays.addAll(employee.getDaysAvailable());
            });
            dto.setSkills(skills);
            dto.setDaysAvailable(avDays);
            dto.setScheduleDate(schedule.getScheduleDate());
            dtos.add(dto);
        });
        return dtos;
    }

    @Override
    public List<ScheduleDTO> getScheduleForPet(Long petId) {
        List<ScheduleDTO> scheduleDTOSForPet = new ArrayList<>();
        Set<EmployeeSkill> skills = new HashSet<>();
        Set<DayOfWeek> avDays = new HashSet<>();
        List<Pet> singlePet = new ArrayList();
        this.getAllSchedules().forEach((schedule) -> {
            schedule.getPets().forEach((pet) -> {
                if (pet.getId().equals(petId)) {
                    ScheduleDTO dto = new ScheduleDTO();
                    dto.setId(schedule.getId());
                    dto.setEmployees(schedule.getEmployees());
                    singlePet.add(pet);
                    dto.setPets(singlePet);

                    schedule.getEmployees().forEach((employee) -> {
                        skills.addAll(employee.getSkills());
                        avDays.addAll(employee.getDaysAvailable());
                    });
                    dto.setSkills(skills);
                    dto.setDaysAvailable(avDays);
                    dto.setScheduleDate(schedule.getScheduleDate());
                    scheduleDTOSForPet.add(dto);
                }
            });
        });
        return scheduleDTOSForPet;
    }

    @Override
    public List<ScheduleDTO> getScheduleForEmployee(Long employeeId) {
        List<ScheduleDTO> scheduleDTOSForEmployee = new ArrayList<>();
        Set<EmployeeSkill> skills = new HashSet<>();
        Set<DayOfWeek> avDays = new HashSet<>();
        List<Employee> singleEmployee = new ArrayList();
        this.getAllSchedules().forEach((schedule) -> {
            schedule.getEmployees().forEach((employee) -> {
                if (employee.getId().equals(employeeId)) {
                    ScheduleDTO dto = new ScheduleDTO();
                    dto.setId(schedule.getId());
                    singleEmployee.add(employee);
                    dto.setEmployees(singleEmployee);
                    dto.setPets(schedule.getPets());

                    schedule.getEmployees().forEach((employee1) -> {
                        skills.addAll(employee1.getSkills());
                        avDays.addAll(employee1.getDaysAvailable());
                    });
                    dto.setSkills(skills);
                    dto.setDaysAvailable(avDays);
                    dto.setScheduleDate(schedule.getScheduleDate());
                    scheduleDTOSForEmployee.add(dto);
                }
            });
        });
        return scheduleDTOSForEmployee;
    }

    @Override
    public List<ScheduleDTO> getScheduleForCustomer(Long customerId) {
        List<ScheduleDTO> scheduleDTOSForCustomer = new ArrayList<>();
        Set<EmployeeSkill> skills = new HashSet<>();
        Set<DayOfWeek> avDays = new HashSet<>();
        List<Pet> singlePet = new ArrayList();
        this.getAllSchedules().forEach((schedule) -> {
            schedule.getPets().forEach((pet) -> {
                if (pet.getCustomer().getId().equals(customerId)) {
                    ScheduleDTO dto = new ScheduleDTO();
                    dto.setId(schedule.getId());
                    dto.setEmployees(schedule.getEmployees());
                    singlePet.add(pet);
                    dto.setPets(singlePet);

                    schedule.getEmployees().forEach((employee1) -> {
                        skills.addAll(employee1.getSkills());
                        avDays.addAll(employee1.getDaysAvailable());
                    });
                    dto.setSkills(skills);
                    dto.setDaysAvailable(avDays);
                    dto.setScheduleDate(schedule.getScheduleDate());
                    scheduleDTOSForCustomer.add(dto);
                }
            });
        });
        return scheduleDTOSForCustomer;
    }

}
