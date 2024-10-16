package com.example.shoponlinepsw.repositories;

import com.example.shoponlinepsw.entities.ProductInPurchase;
import com.example.shoponlinepsw.entities.Purchase;
import com.example.shoponlinepsw.entities.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductInPurchaseRepository extends JpaRepository<ProductInPurchase, Integer> {



    @Query("SELECT pip " +
            "FROM ProductInPurchase pip JOIN Product p ON pip.product.id = p.id " +
            "WHERE pip.product.id = p.id AND p.seller = :s")
    Page<ProductInPurchase> findBySeller(Seller s, Pageable page);


}
