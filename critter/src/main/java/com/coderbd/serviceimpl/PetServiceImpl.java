package com.coderbd.serviceimpl;

import com.coderbd.dto.CustomerDTO;
import com.coderbd.dto.PetDTO;
import com.coderbd.entity.Customer;
import com.coderbd.entity.Pet;
import com.coderbd.repository.PetRepository;
import com.coderbd.service.CustomerService;
import com.coderbd.service.PetService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class PetServiceImpl implements PetService {
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private CustomerService customerService;

    @Override
    public PetDTO save(PetDTO petDTO) {
        return copFromPetToPetDto(this.petRepository.save(coptFromPetDtoToPet(petDTO)));
    }

    @Override
    public PetDTO update(PetDTO petDTO) {
        if (petDTO.getId() != 0 && this.petRepository.existsById(petDTO.getId())) {
            return copFromPetToPetDto(this.petRepository.save(coptFromPetDtoToPet(petDTO)));
        }
        return null;
    }

    @Override
    public PetDTO findById(Long id) {
        return copFromPetToPetDto(this.petRepository.getOne(id));
    }

    @Override
    public boolean isExists(Long id) {
        if (id != null || id != 0) {
            return this.petRepository.existsById(id);
        }
        return false;
    }

    @Override
    public List<PetDTO> findAllPets() {
        List<PetDTO> dtos = new ArrayList<>();
        this.petRepository.findAll().forEach((pet) -> {
            PetDTO dto = copFromPetToPetDto(pet);
            dto.setOwnerId(pet.getCustomer().getId());
            dtos.add(dto);
        });
        return dtos;
    }

    @Override
    public List<PetDTO> findAllPetsByOwner(Long id) {
        List<PetDTO> dtoList = new ArrayList<>();
        if (this.customerService.isExists(id)) {
            this.petRepository.findAllByCustomer(this.customerService.copyFromCustomerDtoToCustomer(customerService.findById(id))).forEach((pet) -> {
                PetDTO dto = copFromPetToPetDto(pet);
                dto.setOwnerId(pet.getCustomer().getId());
                dtoList.add(dto);
            });
        }

        return dtoList;
    }

    @Override
    public List<Pet> findAllByOwner(Long id) {
        List<Pet> pets = new ArrayList<>();
        if (this.customerService.isExists(id)) {
            pets = this.petRepository.findAllByCustomer(this.customerService.copyFromCustomerDtoToCustomer(customerService.findById(id)));
        }

        return pets;
    }

    @Override
    public CustomerDTO findByPetId(Long id) {
        CustomerDTO dto = new CustomerDTO();
        if (petRepository.existsById(id)) {
            Pet pet = this.petRepository.getOne(id);
            dto.setId(pet.getCustomer().getId());
            dto.setName(pet.getCustomer().getName());
            dto.setPhoneNumber(pet.getCustomer().getPhoneNumber());
            dto.setNotes(pet.getCustomer().getNotes());
            dto.setPets(petRepository.findAllByCustomer(pet.getCustomer()));
        }
        return dto;
    }

    public PetDTO copFromPetToPetDto(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setOwnerId(pet.getCustomer().getId());
        petDTO.setType(pet.getPetType());
        petDTO.setName(pet.getName());
        petDTO.setId(pet.getId());
        return petDTO;
    }
@Override
    public Pet coptFromPetDtoToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setId(petDTO.getId());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setPetType(petDTO.getType());
        pet.setName(petDTO.getName());
        pet.setNotes(petDTO.getNotes());
        pet.setCustomer(new Customer(petDTO.getOwnerId()));
       // pet.setCustomer(this.customerService.copyFromCustomerDtoToCustomer(this.customerService.findById(petDTO.getOwnerId())));
        return pet;
    }
}
