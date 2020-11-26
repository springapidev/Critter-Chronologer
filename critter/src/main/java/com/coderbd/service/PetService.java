package com.coderbd.service;


import com.coderbd.dto.CustomerDTO;
import com.coderbd.dto.PetDTO;
import com.coderbd.entity.Pet;

import java.util.List;

public interface PetService {
    PetDTO save(PetDTO petDTO);
    PetDTO update(PetDTO petDTO);
    PetDTO findById(Long id);
    boolean isExists(Long id);
    List<PetDTO> findAllPets();
    List<PetDTO> findAllPetsByOwner(Long id);
    List<Pet> findAllByOwner(Long id);
    CustomerDTO findByPetId(Long id);
    PetDTO copFromPetToPetDto(Pet pet);
    Pet coptFromPetDtoToPet(PetDTO petDTO);
}
