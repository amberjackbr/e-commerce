package com.example.shoponlinepsw.repositories;

import com.example.shoponlinepsw.entities.Buyer;
import com.example.shoponlinepsw.entities.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    Page<Purchase> findByBuyer(Buyer buyer, Pageable page);

}
