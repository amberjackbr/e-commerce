package com.example.shoponlinepsw.controllers;


import com.example.shoponlinepsw.entities.Buyer;
import com.example.shoponlinepsw.entities.Cart;
import com.example.shoponlinepsw.entities.Seller;
import com.example.shoponlinepsw.repositories.BuyerRepository;
import com.example.shoponlinepsw.services.BuyerService;
import com.example.shoponlinepsw.services.SellerService;
import com.example.shoponlinepsw.support.ResponseMessage;
import com.example.shoponlinepsw.support.exceptions.MailAlredyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/access")
public class AccessController {//unused class

    @Autowired
    private BuyerService buyerService;
    @Autowired
    private SellerService sellerService;

    @PostMapping
    @RequestMapping("/createbuyer")
    @Transactional
    public ResponseEntity createBuyer(@RequestBody @Validated Buyer buyer){
        try{
            Buyer added = buyerService.registerNewUser(buyer);
            Cart cart = new Cart();
            cart.setBuyer(added);
            Cart newCart = buyerService.createNewCart(cart);
            added.setCart(newCart);
            return new ResponseEntity(added, HttpStatus.OK);
        }catch(MailAlredyExistException e){
            return new ResponseEntity<>(new ResponseMessage("MailAlreadyExisting"), HttpStatus.BAD_REQUEST);
        }
    }//ok


    @PostMapping
    @RequestMapping("/createseller")
    public ResponseEntity createSeller(@RequestBody @Validated Seller seller){
        try{
            Seller added = sellerService.registerNewSeller(seller);
            return new ResponseEntity(added, HttpStatus.OK);
        }catch(MailAlredyExistException e){
            return new ResponseEntity<>(new ResponseMessage("MailAlreadyExisting"), HttpStatus.BAD_REQUEST);
        }
    }//ok

    @GetMapping("/trova")
    public ResponseEntity trova(){//todelete
        //Buyer buyer = BuyerService
        return new ResponseEntity<>("prova", HttpStatus.OK);
    }//toDelete



}