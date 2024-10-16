package com.example.shoponlinepsw.repositories;

import com.example.shoponlinepsw.entities.Buyer;
import com.example.shoponlinepsw.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Integer> {

    Seller findById(int id);
    List<Seller> findByFirstName(String firstName);

    List<Seller> findByLastName(String lastName);

    List<Seller> findByTelephoneNumber(String telephoneNumber);

    List<Seller> findByFirstNameAndLastName(String firstname, String lastName);

    Seller findByEmail(String email);

    Boolean existsByEmail(String email);
}
