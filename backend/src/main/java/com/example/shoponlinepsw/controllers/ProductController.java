package com.example.shoponlinepsw.controllers;


import com.example.shoponlinepsw.entities.Buyer;
import com.example.shoponlinepsw.entities.Cart;
import com.example.shoponlinepsw.entities.Product;
import com.example.shoponlinepsw.entities.Purchase;
import com.example.shoponlinepsw.services.BuyerService;
import com.example.shoponlinepsw.services.ProductService;
import com.example.shoponlinepsw.support.ResponseMessage;
import com.example.shoponlinepsw.support.authentication.Utils;
import com.example.shoponlinepsw.support.exceptions.MailAlredyExistException;
import com.example.shoponlinepsw.support.exceptions.UnavaiableQuantityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;




    @GetMapping
    public ResponseEntity showProduct(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber, @RequestParam(value = "pageSize", defaultValue = "1000") int pageSize, @RequestParam(value = "sortBy", defaultValue = "id") String sortBy){
        List<Product> result = productService.showProduct(pageNumber, pageSize, sortBy);
        if(result.size() <= 0){
            return new ResponseEntity<>(new ResponseMessage("No results!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



}