package com.coderbd.serviceimpl;

import com.coderbd.dto.ScheduleDTO;
import com.coderbd.entity.Schedule;
import com.coderbd.enums.EmployeeSkill;
import com.coderbd.repository.ScheduleRepository;
import com.coderbd.service.EmployeeService;
import com.coderbd.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private EmployeeService employeeService;

    @Override
    public void createSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();

        scheduleDTO.getEmployees().forEach((e) -> {
            schedule.getEmployees().add(e);
        });

        scheduleDTO.getPets().forEach((p) -> {
            schedule.getPets().add(p);
        });

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

        List<Schedule> schedulesMatched = new ArrayList<>();
        List<Schedule> schedules = this.scheduleRepository.findAll();

        schedules.forEach((schedule) -> {
            schedule.getPets().forEach((pet) -> {
                if (pet.getId().equals(petId)) {
                    schedulesMatched.add(schedule);
                }
            });
        });


        return convertSchedulesToSchedulesDTO(schedulesMatched);
    }

    @Override
    public List<ScheduleDTO> getScheduleForEmployee(Long employeeId) {

        List<Schedule> schedulesMatched = new ArrayList<>();
        List<Schedule> schedules = this.scheduleRepository.findAll();

       schedules.forEach((schedule) -> {
            schedule.getEmployees().forEach((employee) -> {
                if (employee.getId().equals(employeeId)) {

                    schedulesMatched.add(schedule);
                }
            });
        });

        return convertSchedulesToSchedulesDTO(schedulesMatched);
    }


    private List<ScheduleDTO> convertSchedulesToSchedulesDTO(List<Schedule> schedules){
       return schedules.stream()
                .map(this::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
    }


    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setEmployees(schedule.getEmployees());
        scheduleDTO.setPets(schedule.getPets());
        scheduleDTO.setDaysAvailable(schedule.getDaysAvailable());
        scheduleDTO.setSkills(schedule.getSkills());
        scheduleDTO.setScheduleDate(schedule.getScheduleDate());
        return scheduleDTO;
    }

    @Override
    public List<ScheduleDTO> getScheduleForCustomer(Long customerId) {

        List<Schedule> schedulesMatched = new ArrayList<>();
        List<Schedule> schedules = this.scheduleRepository.findAll();

        schedules.forEach((schedule) -> {
            schedule.getPets().forEach((pet) -> {
                if (pet.getCustomer().getId().equals(customerId)) {
                    schedulesMatched.add(schedule);
                }
            });
        });

        return convertSchedulesToSchedulesDTO(schedulesMatched);
    }

}
