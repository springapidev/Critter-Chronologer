package com.coderbd.controller;

import com.coderbd.dto.PetDTO;
import com.coderbd.service.CustomerService;
import com.coderbd.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private PetService petService;
    @Autowired
    private CustomerService customerService;

    /**
     * @Apinote we shall provide petDTO and it will convert to pet entity object and save
     * @param petDTO
     * @return CREATED means 201
     */
    @PostMapping
    public ResponseEntity<PetDTO> savePet(@RequestBody PetDTO petDTO) {
        petDTO.setBirthDate(LocalDate.now());
        PetDTO dto = this.petService.save(petDTO);
        return new ResponseEntity<PetDTO>(dto,HttpStatus.CREATED);
    }

    /**
     * @Apinote first check petId is exists or not, if exits, expected results will be provied
     * @param petId
     * @return obj of PetDTO OR 404
     */
    @GetMapping("/{petId}")
    public ResponseEntity<PetDTO> getPet(@PathVariable Long petId) {
        if (petService.isExists(petId)) {
            return new ResponseEntity<PetDTO>(this.petService.findById(petId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> getPets() {
        return new ResponseEntity<List<PetDTO>>(this.petService.findAllPets(), HttpStatus.OK);
    }

    /**
     * @Apinote first check ownerId is exists or not, if exits, expected results will be provied
     * @param ownerId
     * @return  obj of List<PetDTO> OR 404
     */
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<PetDTO>> getPetsByOwner(@PathVariable Long ownerId) {
        if (this.customerService.isExists(ownerId)) {
            return new ResponseEntity<List<PetDTO>>(this.petService.findAllPetsByOwner(ownerId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
