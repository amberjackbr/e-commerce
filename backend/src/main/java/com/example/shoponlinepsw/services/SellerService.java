package com.example.shoponlinepsw.services;

import com.example.shoponlinepsw.entities.Buyer;
import com.example.shoponlinepsw.entities.ProductInPurchase;
import com.example.shoponlinepsw.entities.Seller;
import com.example.shoponlinepsw.repositories.ProductInPurchaseRepository;
import com.example.shoponlinepsw.repositories.SellerRepository;
import com.example.shoponlinepsw.support.exceptions.MailAlredyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ProductInPurchaseRepository productInPurchaseRepository;

    @Transactional(readOnly = true)
    public List<Seller> findAll(){
        return sellerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Seller findSeller(int id){
        if(sellerRepository.existsById(id)){
            return sellerRepository.findById(id);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Seller findSellerByEmail(String email){
        if(sellerRepository.existsByEmail(email)){
            return sellerRepository.findByEmail(email);
        }
        return null;
    }


    @Transactional(readOnly = false)
    public Seller registerNewSeller(Seller seller) throws MailAlredyExistException {
        if(sellerRepository.existsByEmail(seller.getEmail())){
            throw new MailAlredyExistException();
        }
        return sellerRepository.save(seller);
    }


    @Transactional(readOnly = true)
    public List<ProductInPurchase> findMySelling(Seller seller, int pageNumber, int pageSize){
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<ProductInPurchase> result = productInPurchaseRepository.findBySeller(seller, page);
        if(result.hasContent()){
            return result.getContent();
        }
        else{
            return new ArrayList<>();
        }
    }


}
