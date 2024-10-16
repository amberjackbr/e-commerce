package com.example.shoponlinepsw.repositories;

import com.example.shoponlinepsw.entities.Cart;
import com.example.shoponlinepsw.entities.Product;
import com.example.shoponlinepsw.entities.ProductInCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInCartRepository extends JpaRepository<ProductInCart, Integer> {

    ProductInCart findById(int id);
    boolean existsByCartAndProduct(Cart cart, Product product);
}
