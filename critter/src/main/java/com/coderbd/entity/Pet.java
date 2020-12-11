package com.coderbd.entity;

import com.coderbd.enums.PetType;
import lombok.Data;

import javax.persistence.*;

import java.time.LocalDate;


/**
 * @author Mohammad Rajaul Islam
 */
@Data
@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type")
    private PetType petType;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Customer customer;
    @Column(name = "birthDate")
    private LocalDate birthDate=LocalDate.now();

    private String notes;
}
