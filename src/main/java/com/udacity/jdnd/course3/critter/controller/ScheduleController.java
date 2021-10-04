package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.entity.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return parseScheduleToDTO(
                scheduleService.save(
                        parseDTOtoSchedule(scheduleDTO),
                        scheduleDTO.getPetIds(),
                        scheduleDTO.getEmployeeIds()
                )
        );
    }

    private ScheduleDTO parseScheduleToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        if (schedule.getEmployees() != null) {
            scheduleDTO.setEmployeeIds(
                    schedule.getEmployees().stream()
                            .map(Employee::getId)
                            .collect(Collectors.toList())
            );
        }
        if (schedule.getPets() != null) {
            scheduleDTO.setPetIds(
                    schedule.getPets().stream()
                            .map(Pet::getId)
                            .collect(Collectors.toList())
            );
        }
//        scheduleDTO.setDate();
        return scheduleDTO;
    }

    private Schedule parseDTOtoSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        return schedule;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.findAll().stream().map(this::parseScheduleToDTO).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleService.findScheduleForPet(petId).stream().map(this::parseScheduleToDTO).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.findScheduleForEmployee(employeeId).stream().map(this::parseScheduleToDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.findScheduleForCustomer(customerId).stream().map(this::parseScheduleToDTO).collect(Collectors.toList());
    }
}
