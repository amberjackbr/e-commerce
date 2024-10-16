package com.example.shoponlinepsw.repositories;

import com.example.shoponlinepsw.entities.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BuyerRepository extends JpaRepository<Buyer,Integer> {

    Buyer findById(int id);
    List<Buyer> findByFirstName(String firstName);

    List<Buyer> findByLastName(String lastName);

    List<Buyer> findByTelephoneNumber(String telephoneNumber);

    List<Buyer> findByFirstNameAndLastName(String firstname, String lastName);

    Buyer findByEmail(String email);

    Boolean existsByEmail(String email);



}
