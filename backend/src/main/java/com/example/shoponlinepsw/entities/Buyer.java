package com.example.shoponlinepsw.entities;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "buyer",schema = "public")
public class Buyer {

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
    @Column(name = "telephone_number", nullable = false, length = 20)
    private String telephoneNumber;

    @Basic
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.MERGE)
    private List<Purchase> purchases;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cart")
    @JsonIgnore
    private Cart cart;



}

