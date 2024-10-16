package com.example.shoponlinepsw.repositories;

import com.example.shoponlinepsw.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByName(String name);

    Product findById(int id);

    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE (p.name LIKE :name OR :name IS NULL) AND" +
            "      (p.price >= :minPrice OR :minPrice IS NULL) AND" +
            "      (p.price <= :maxPrice OR :maxPrice IS NULL)")
    List<Product> advancedSearch(String name, Float minPrice, Float maxPrice);


    @Query("SELECT p " +
            "FROM Product p JOIN ProductInPurchase pip ON p.id = pip.product.id " +
            "WHERE (p.id = pip.product.id) " +
            "GROUP BY p.id " +
            "order by count(p.id) desc ")
    Page<Product> mostSold(Pageable page);


}
