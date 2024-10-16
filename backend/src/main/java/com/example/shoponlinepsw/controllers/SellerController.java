package com.example.shoponlinepsw.controllers;

import com.example.shoponlinepsw.entities.Buyer;
import com.example.shoponlinepsw.entities.Product;
import com.example.shoponlinepsw.entities.ProductInPurchase;
import com.example.shoponlinepsw.entities.Seller;
import com.example.shoponlinepsw.services.ProductService;
import com.example.shoponlinepsw.services.SellerService;
import com.example.shoponlinepsw.support.ResponseMessage;
import com.example.shoponlinepsw.support.authentication.Utils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
@SecurityRequirement(name = "keycloak")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private ProductService productService;



    @GetMapping
    @RequestMapping("/myself")
    public ResponseEntity find(){
        String email = Utils.getEmail();
        Seller result = sellerService.findSellerByEmail(email);
        if(result == null){
            return new ResponseEntity<>(new ResponseMessage("no user found with this mail"), HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }//works properly


    @GetMapping
    @RequestMapping("/all")
    public ResponseEntity findAllSellers(){
        List<Seller> result = sellerService.findAll();
        if(result == null){
            return new ResponseEntity<>(new ResponseMessage("no user found"),HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }//ok but unused


    @GetMapping
    @RequestMapping("myproducts")
    public ResponseEntity findMyProducts(){
        String email = Utils.getEmail();
        Seller me = sellerService.findSellerByEmail(email);
        if(me == null){
            return new ResponseEntity<>(new ResponseMessage("no user found with this email"), HttpStatus.OK);
        }
        List<Product> result = me.getProductList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }//works properly


    @GetMapping
    @RequestMapping("/myselling")
    public ResponseEntity findMySelling(){
        String email = Utils.getEmail();
        Seller me = sellerService.findSellerByEmail(email);
        if(me == null){
            return new ResponseEntity<>(new ResponseMessage("no user found with this email"), HttpStatus.OK);
        }
        List<ProductInPurchase> result = sellerService.findMySelling(me,0,100);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }//totest and fix paging //for now works with empty list




    @PostMapping
    @RequestMapping("/addproduct")
    public ResponseEntity createProduct(@RequestBody @Validated Product product){
        String email = Utils.getEmail();
        Seller me = sellerService.findSellerByEmail(email);
        if(me == null){
            return new ResponseEntity<>(new ResponseMessage("No account linked to this mail"), HttpStatus.BAD_REQUEST);
        }
        product.setSeller(me);
        try{
            Product added = productService.addProduct(product);
            return new ResponseEntity(added, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(new ResponseMessage("Something went wrong, retry later"), HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping
    @RequestMapping("/setproductqt")
    public ResponseEntity setQuantity(@RequestBody @Validated Product product){
        String email = Utils.getEmail();
        Seller me = sellerService.findSellerByEmail(email);
        Product p = productService.findProduct(product.getId());
        if(me.getId() == p.getSeller().getId()){
            Product result = productService.changeQuantity(product);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseMessage("Wrong seller id or not your product"), HttpStatus.BAD_REQUEST);
    }

}
