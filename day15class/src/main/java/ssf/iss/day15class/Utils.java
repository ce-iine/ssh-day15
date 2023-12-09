package ssf.iss.day15class;

import java.util.LinkedList;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import ssf.iss.day15class.models.Item;

public class Utils { // handles creation of new session 

    public static final String ATTR_CART ="cart";
    public static final String BEAN_REDIS ="myredis";
    public static final Integer FIELD_NAME =0;
    public static final Integer FIELD_QUANTITY =1;


    public static List<Item> getCart (HttpSession sess){
        List<Item> cart = (List<Item>) (sess.getAttribute(ATTR_CART));
        if(null==cart){ // empty cart means new session 
            cart = new LinkedList<>();
            sess.setAttribute(ATTR_CART, cart);
        } 
        return cart;
    }
    
}
