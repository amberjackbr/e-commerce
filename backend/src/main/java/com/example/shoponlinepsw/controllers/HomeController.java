package com.example.shoponlinepsw.controllers;


import com.example.shoponlinepsw.entities.Product;
import com.example.shoponlinepsw.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class HomeController {

    @Autowired
    private ProductService productService;


    @GetMapping
    public List<Product> getTop(){return productService.showMostSold(0,10);}



}
