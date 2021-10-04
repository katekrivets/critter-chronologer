package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.common.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.NoSuchObjectException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(long employeeId) throws NoSuchObjectException {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            return employee.get();
        } else {
            throw new NoSuchObjectException(String.format("EMPLOYEE WITH id: %s WAS NOT FOUND", employeeId));
        }
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) throws NoSuchObjectException {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            employee.get().setDaysAvailable(daysAvailable);
            employeeRepository.save(employee.get());

        } else {
            throw new NoSuchObjectException(String.format("EMPLOYEE WITH id: %s WAS NOT FOUND", employeeId));
        }
    }

    public List<Employee> getAvailability(Set<EmployeeSkill> skills, LocalDate date) {
        return employeeRepository.findByDaysAvailableContainingAndSkillsIn(date.getDayOfWeek(), skills, skills.size());
    }
}
