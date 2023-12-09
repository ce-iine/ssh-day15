package ssf.iss.day15class.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssf.iss.day15class.models.Item;
import ssf.iss.day15class.repositories.CartRepository;

@Service
public class CartService {

    @Autowired 
    private CartRepository cartRepo;
    
    public List<Item> getCart(String name){
        if(cartRepo.hasCart(name)){
            return cartRepo.getCart(name);
        } return new LinkedList<>();
    }

    public void save (String name, List<Item> cart){
        cartRepo.deleteCart(name);
        cartRepo.addCart(name, cart);
    }
    
}
