package com.example.shoponlinepsw.repositories;

import com.example.shoponlinepsw.entities.Buyer;
import com.example.shoponlinepsw.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {


    Cart findById(int id);
    Cart findByBuyer(Buyer buyer);





}
