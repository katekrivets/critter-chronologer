package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        try {
            return parsePetToPetDto(
                    petService.save(
                            parsePetDtoToPet(petDTO),
                            petDTO.getOwnerId()
                    )
            );
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{petId}")
    public PetDTO savePetById(@RequestBody PetDTO petDTO, @PathVariable long petId) {
        petDTO.setId(petId);
        try {
            return parsePetToPetDto(
                    petService.save(
                            parsePetDtoToPet(petDTO),
                            petDTO.getOwnerId()
                    )
            );
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        try {
            return parsePetToPetDto(petService.findById(petId));
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping
    public List<PetDTO> getPets() {
        List<Pet> petList = petService.findAll();
        return parsePetsToDTOs(petList);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> petList = petService.findPetsByOwnerId(ownerId);
        return parsePetsToDTOs(petList);
    }

    private PetDTO parsePetToPetDto(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getOwner().getId());
        return petDTO;
    }

    private Pet parsePetDtoToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }

    private List<PetDTO> parsePetsToDTOs(List<Pet> petList) {
        List<PetDTO> petDTOList = new ArrayList<>();
        if (!petList.isEmpty()) {
            petDTOList = petList.stream().map(this::parsePetToPetDto).collect(Collectors.toList());
        }
        return petDTOList;
    }
}
