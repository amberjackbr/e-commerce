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
@Table(name = "product",schema = "public")
public class Product {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Basic
    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Basic
    @Column(name = "price", nullable = false)
    private Float price;

    @Basic
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Version
    @Column(name = "version", nullable = false)
    private long version;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private List<ProductInPurchase> sellingList;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private List<ProductInCart> cartList;

    @ManyToOne
    @JoinColumn(name = "seller")
    private Seller seller;







}
