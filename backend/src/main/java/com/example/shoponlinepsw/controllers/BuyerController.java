package com.example.shoponlinepsw.controllers;

import com.example.shoponlinepsw.entities.*;
import com.example.shoponlinepsw.services.BuyerService;
import com.example.shoponlinepsw.services.ProductService;
import com.example.shoponlinepsw.support.ResponseMessage;
import com.example.shoponlinepsw.support.authentication.JwtAuthenticationConverter;
import com.example.shoponlinepsw.support.authentication.Utils;
import com.example.shoponlinepsw.support.exceptions.AlredyInCartException;
import com.example.shoponlinepsw.support.exceptions.UnavaiableQuantityException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/buyer")
@SecurityRequirement(name = "keycloak")
public class BuyerController {

    @Autowired
    private BuyerService buyerService;
    @Autowired
    private ProductService productService;

    @GetMapping
    @RequestMapping("/myself")
    public ResponseEntity find(){
        String email = Utils.getEmail();
        Buyer result = buyerService.findUserByMail(email);
        if(result == null){
            return new ResponseEntity<>(new ResponseMessage("no user found with id "), HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }//works properly

    @GetMapping
    @RequestMapping("/all")
    public ResponseEntity findAllBuyers(){
        List<Buyer> result = buyerService.findAll();
        if(result == null){
            return new ResponseEntity<>(new ResponseMessage("no user found"),HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }//works but not used



    @GetMapping
    @RequestMapping("/mypurchases")
    public ResponseEntity findMyPurchasesnew(){
        String email = Utils.getEmail();
        Buyer me = buyerService.findUserByMail(email);
        List<Purchase> result = me.getPurchases();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }//works with empty purchases, test with purchases and maybe page result



    @GetMapping
    @RequestMapping("/cart")
    public ResponseEntity findMyCartnew(){
        String email = Utils.getEmail();
        Buyer me = buyerService.findUserByMail(email);
        Cart result = me.getCart();
        //from
        List<Product> newResult = new ArrayList<Product>();
        List<ProductInCart> pic = result.getCartList();
        for(int i = 0; i < pic.size(); i++){
            newResult.add(pic.get(i).getProduct());
        }
        //to
        return new ResponseEntity<>(newResult, HttpStatus.OK);
    }//works properly but useless since only get cart id




    @PostMapping
    @RequestMapping("/addtocartnew2")
    public ResponseEntity addToCartnew2(@RequestBody @Validated Product product){
        String email = Utils.getEmail();
        Buyer me = buyerService.findUserByMail(email);
        ProductInCart pip = new ProductInCart();
        pip.setCart(me.getCart());
        pip.setProduct(product);
        try {
            ProductInCart added = buyerService.addToCart(pip);
            return new ResponseEntity<>(added, HttpStatus.OK);
        } catch (AlredyInCartException e) {
            return new ResponseEntity<>(new ResponseMessage("Already in cart"), HttpStatus.BAD_REQUEST);
        }
    }//work as intended just need product id (fix error message when product doesn't exist)



    @PostMapping//dovrebbe essere una deletemapping
    @RequestMapping("/removefromcartnew2")
    public ResponseEntity removeFromCartnew2(@RequestBody @Validated Product product){
        String email = Utils.getEmail();
        Buyer me = buyerService.findUserByMail(email);
        List<ProductInCart> piplist = me.getCart().getCartList();
        for(int i = 0; i < piplist.size(); i++){
            if(piplist.get(i).getProduct().getId() == product.getId()){
                if(buyerService.removeFromCart(piplist.get(i))){
                    return new ResponseEntity<>(new ResponseMessage("Removed from cart"), HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(new ResponseMessage("Item was not in your cart"), HttpStatus.BAD_REQUEST);
    }//works properly



    @PostMapping
    @RequestMapping("/buy")
    public ResponseEntity addPurchase(@RequestBody @Validated Purchase purchase){
        String email = Utils.getEmail();
        Buyer me = buyerService.findUserByMail(email);
        try{
            purchase.getBuyer().setId(me.getId());
            Purchase added = productService.newPurchase(purchase, email);
            return new ResponseEntity<>(added, HttpStatus.OK);
        }catch(UnavaiableQuantityException e){
            return new ResponseEntity<>(new ResponseMessage("Quantità non disponibile"), HttpStatus.BAD_REQUEST);
        }
    }//works properly


}
