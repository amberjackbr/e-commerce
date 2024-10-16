package com.example.shoponlinepsw.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "product_in_purchase",schema = "public")
public class ProductInPurchase {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "purchase")
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;



}






















