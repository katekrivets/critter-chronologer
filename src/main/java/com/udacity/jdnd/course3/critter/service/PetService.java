package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    @Transactional
    public Pet save(Pet pet, Long ownerId) throws NoSuchObjectException {
        Optional<Customer> customer = customerRepository.findById(ownerId);
        if (customer.isPresent()) {
            pet.setOwner(customer.get());
        } else {
            throw new NoSuchObjectException(String.format("CUSTOMER WITH id: %s WAS NOT FOUND", ownerId));
        }
        return petRepository.save(pet);
    }

    public Pet findById(long petId) throws NoSuchObjectException{
        Optional<Pet> pet = petRepository.findById(petId);
        if(pet.isPresent()) {
            return pet.get();
        } else  {
            throw new NoSuchObjectException(String.format("PET WITH id: %s WAS NOT FOUND", petId));
        }
    }

    public List<Pet> findPetsByOwnerId(long ownerId) {
        return petRepository.findAllByOwnerId(ownerId);
    }
}
