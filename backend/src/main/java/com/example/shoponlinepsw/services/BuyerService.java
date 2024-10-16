package com.example.shoponlinepsw.services;


import com.example.shoponlinepsw.entities.*;
import com.example.shoponlinepsw.repositories.BuyerRepository;
import com.example.shoponlinepsw.repositories.CartRepository;
import com.example.shoponlinepsw.repositories.ProductInCartRepository;
import com.example.shoponlinepsw.repositories.PurchaseRepository;
import com.example.shoponlinepsw.support.exceptions.AlredyInCartException;
import com.example.shoponlinepsw.support.exceptions.MailAlredyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuyerService {

    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductInCartRepository productInCartRepository;

    @Transactional(readOnly = false)
    public Buyer registerNewUser(Buyer buyer) throws MailAlredyExistException {
        if(buyerRepository.existsByEmail(buyer.getEmail())){
            throw new MailAlredyExistException();
        }
        return buyerRepository.save(buyer);
    }

    @Transactional(readOnly = false)
    public Cart createNewCart(Cart cart){
        return cartRepository.save(cart);
    }

    @Transactional(readOnly = true)
    public List<Purchase> showMyPurchases(Buyer buyer,int pageNumber, int pageSize){
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Purchase> result = purchaseRepository.findByBuyer(buyer,page);
        if(result.hasContent()){
            return result.getContent();
        }
        else{
            return new ArrayList<Purchase>();
        }
    }


    @Transactional(readOnly = true)
    public Buyer findUser(int id){
        if(buyerRepository.existsById(id)){
            return buyerRepository.findById(id);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Buyer findUserByMail(String email){
        if(buyerRepository.existsByEmail(email)){
            return buyerRepository.findByEmail(email);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Buyer> findAll(){
        return buyerRepository.findAll();
    }

    @Transactional(readOnly = false)
    public ProductInCart addToCart(ProductInCart productInCart) throws AlredyInCartException{
        if(productInCartRepository.existsByCartAndProduct(productInCart.getCart(),productInCart.getProduct())){
            throw new AlredyInCartException();
        }
        return productInCartRepository.save(productInCart);
    }

    @Transactional(readOnly = true)
    public Cart findCart(int id){
        if (cartRepository.existsById(id)){
            return cartRepository.findById(id);
        }
        return null;
    }

    @Transactional(readOnly = false)
    public boolean removeFromCart(ProductInCart productInCart){
        if(productInCartRepository.existsById(productInCart.getId())){
            productInCartRepository.deleteById(productInCart.getId());
            return true;
        }
        return false;
    }







}
