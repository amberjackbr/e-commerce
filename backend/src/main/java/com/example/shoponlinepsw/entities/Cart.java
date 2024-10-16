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
@Table(name = "cart", schema = "public")
public class Cart {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "buyer")
    @JsonIgnore
    private Buyer buyer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<ProductInCart> cartList;


}
