package com.coderbd.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author Mohammad Rajaul Islam
 */
@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    private String phoneNumber;
    private String notes;
    @JsonIgnore
    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Pet> pets;

    public Customer() {
    }
    public Customer(Long id) {
        this.id=id;
    }
}
