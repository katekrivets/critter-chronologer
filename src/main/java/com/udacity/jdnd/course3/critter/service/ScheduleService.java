package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    PetRepository petRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    public Schedule save(Schedule schedule, List<Long> petIds, List<Long> employeeIds) {
        if (petIds != null && !petIds.isEmpty()) {
            List<Pet> pets = petRepository.findAllById(petIds);
            schedule.setPets(pets);
        }
        if (employeeIds != null && !employeeIds.isEmpty()) {
            List<Employee> employees = employeeRepository.findAllById(employeeIds);
            schedule.setEmployees(employees);
        }
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findScheduleForPet(long petId) {
        return scheduleRepository.findAllByPetsId(petId);
    }

    public List<Schedule> findScheduleForEmployee(long employeeId) {
        return scheduleRepository.findAllByEmployeesId(employeeId);
    }

    public List<Schedule> findScheduleForCustomer(long customerId) {
        List<Pet> pets = petRepository.findAllByOwnerId(customerId);
        List<Schedule> schedules = new ArrayList<>();
        for (Pet pet : pets) {
            schedules.addAll(scheduleRepository.findAllByPetsId(pet.getId()));
        }
        return schedules;
    }
}
