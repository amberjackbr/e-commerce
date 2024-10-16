package com.example.shoponlinepsw.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "seller",schema = "public")
public class Seller {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Basic
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Basic
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Basic
    @Column(name = "telephone_number", nullable = false, length = 20)
    private String telephoneNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "seller", cascade = CascadeType.MERGE)
    private List<Product> productList;


}
