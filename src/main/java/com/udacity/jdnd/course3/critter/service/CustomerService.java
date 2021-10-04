package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PetRepository petRepository;

    public Customer save(Customer customer, List<Long> petIds) {
        if (petIds != null && !petIds.isEmpty()) {
            List<Pet> pets = petRepository.findAllById(petIds);
            customer.setPets(pets);
        }
        return customerRepository.save(customer);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findCustomerByPetId(Long petId) {
        return customerRepository.findByPetsId(petId);
    }
}
