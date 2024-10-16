package com.example.shoponlinepsw.controllers;

import com.example.shoponlinepsw.entities.Buyer;
import com.example.shoponlinepsw.entities.ProductInPurchase;
import com.example.shoponlinepsw.entities.Purchase;
import com.example.shoponlinepsw.repositories.ProductInPurchaseRepository;
import com.example.shoponlinepsw.repositories.PurchaseRepository;
import com.example.shoponlinepsw.support.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/prova")
public class provaController {//da eliminare

    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ProductInPurchaseRepository productInPurchaseRepository;

    @GetMapping
    @RequestMapping("/p")
    public ResponseEntity findAllP(){
        List<Purchase> result = purchaseRepository.findAll();
        if(result == null){
            return new ResponseEntity<>(new ResponseMessage("no user found"), HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping
    @RequestMapping("/pip")
    public ResponseEntity findAllPIP(){
        List<ProductInPurchase> result = productInPurchaseRepository.findAll();
        if(result.isEmpty()){
            return new ResponseEntity<>(new ResponseMessage("no user found"), HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
