package com.coderbd.service;

import com.coderbd.dto.ScheduleDTO;
import com.coderbd.entity.Schedule;

import java.util.List;

public interface ScheduleService {
//    ScheduleDTO createSchedule(ScheduleDTO scheduleDTO);
//    List<ScheduleDTO> getAllSchedules();
//    ScheduleDTO getScheduleById(Long cheduleId);
//    List<ScheduleDTO> getScheduleForPet(Long petId);
//    List<ScheduleDTO> getScheduleForEmployee(Long employeeId);
//    List<ScheduleDTO> getScheduleForCustomer(Long customerId);
void createSchedule(ScheduleDTO scheduleDTO);
    List<ScheduleDTO> getAllSchedules();
    List<ScheduleDTO> getScheduleForPet(Long petId);
    List<ScheduleDTO> getScheduleForEmployee(Long employeeId);
    List<ScheduleDTO> getScheduleForCustomer(Long customerId);
}
