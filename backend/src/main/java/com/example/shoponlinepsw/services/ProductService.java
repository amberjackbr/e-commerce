package com.example.shoponlinepsw.services;

import com.example.shoponlinepsw.entities.*;
import com.example.shoponlinepsw.repositories.ProductInCartRepository;
import com.example.shoponlinepsw.repositories.ProductInPurchaseRepository;
import com.example.shoponlinepsw.repositories.ProductRepository;
import com.example.shoponlinepsw.repositories.PurchaseRepository;
import com.example.shoponlinepsw.support.exceptions.UnavaiableQuantityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ProductInPurchaseRepository productInPurchaseRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ProductInCartRepository productInCartRepository;

    @Transactional(readOnly = false)
    public Product addProduct(Product newProduct){
        return productRepository.save(newProduct);
    }

    @Transactional(readOnly = true)
    public List<Product> showMostSold(int pageNumber, int pageSize){
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Product> result = productRepository.mostSold(page);
        if(result.hasContent()){
            return result.getContent();
        }
        else{
            return new ArrayList<Product>();
        }
    }

    @Transactional(readOnly = true)
    public List<Product> showProduct(int pageNumber, int pageSize, String sortBy){
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Product> result = productRepository.findAll(page);
        if(result.hasContent()){
            return result.getContent();
        }
        else{
            return new ArrayList<Product>();
        }

    }

    @Transactional(readOnly = false,rollbackFor = UnavaiableQuantityException.class)
    public Purchase newPurchase(Purchase p, String email) throws UnavaiableQuantityException {
        //Buyer me = buyerService.findUserByMail(email);
        //p.setBuyer(me);
        Purchase result = purchaseRepository.save(p);
        for(ProductInPurchase productInPurchase : result.getProductList()){
            productInPurchase.setPurchase(result);
            ProductInPurchase justAdded = productInPurchaseRepository.save(productInPurchase);
            //entityManager.refresh(justAdded);
            Product product = justAdded.getProduct();
            int newQuantity = product.getQuantity() - justAdded.getQuantity();
            if(newQuantity < 0){
                throw new UnavaiableQuantityException();
            }
            else{
                product.setQuantity(newQuantity);
               // entityManager.refresh(productInPurchase);
            }
        }
        entityManager.refresh(result);
        return result;
    }

    /*@Transactional(readOnly = false)
    public ProductInCart addToCart(ProductInCart productInCart){
        productInCartRepository.save(productInCart);
        return productInCart;
    }*/

    @Transactional(readOnly = true)
    public Product findProduct(int id){
        if(productRepository.existsById(id)){
            return productRepository.findById(id);
        }
        return null;
    }


    @Transactional(readOnly = false)
    public Product changeQuantity(Product product){
        Product result =  productRepository.findById(product.getId());
        result.setQuantity(product.getQuantity());
        productRepository.save(result);
        return result;
    }

    @Transactional(readOnly = true)
    public ProductInCart findProductInCart(int id){
        if (productInCartRepository.existsById(id)){
            return productInCartRepository.findById(id);
        }
        return null;
    }




    //top seller methods to be done

}

















