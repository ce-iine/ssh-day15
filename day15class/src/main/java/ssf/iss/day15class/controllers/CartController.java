package ssf.iss.day15class.controllers;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import ssf.iss.day15class.Utils;
import ssf.iss.day15class.models.Item;
import ssf.iss.day15class.services.CartService;

@Controller
@RequestMapping("/cart") // never put verb in resource name eg. add-cart
public class CartController {

    private Logger logger = Logger.getLogger(CartController.class.getName());

    public static final String ATTR_ITEM = "item";
    public static final String ATTR_CART = "cart";
    public static final String ATTR_USERNAME = "username";

    @Autowired
    private CartService cartSvc;

    @GetMapping
    public String getCart(@RequestParam String name, Model model, HttpSession session) { //requestparam send info btwn front end and back end

        List<Item> cart = cartSvc.getCart(name);

        logger.info("Cart: %s, %s\n".formatted(name, cart));

        session.setAttribute(Utils.ATTR_CART, cart);

        model.addAttribute(ATTR_ITEM, new Item());
        model.addAttribute(ATTR_CART, cart);
        model.addAttribute(ATTR_USERNAME, name); // hidden in html 

        return "cart";
    }

    // List<Item> cart = new LinkedList<>(); // dont put here bc one controller is shared by many users
    // if you enter items from one window, go to another window and enter items, the
    // item adds on to the same cart

    @PostMapping("/checkout")
    public String postCartCheckout(HttpSession sess, @RequestParam String username) {
        // ModelAndView mav = new ModelAndView("cart");

        List<Item> cart = Utils.getCart(sess);
        System.out.printf("checking out cart: %s\n", cart);

        cartSvc.save(username, cart);
        // get name by putting it in session - cannot put in database because need name
        // to access database

        sess.invalidate(); // kills session

        // mav.addObject(ATTR_CART, cart);
        // mav.setStatus(HttpStatusCode.valueOf(200));

        return "redirect:/";

    }

    @PostMapping
    public ModelAndView postCart(@Valid @ModelAttribute(ATTR_ITEM) Item item,
            BindingResult bindings, HttpSession sess,
            @RequestParam String username, @RequestBody String body) { // session is like a map - sessions stored in memory, or in database
    // requestparam specific parameter
    // requestbody returns whole string body

        System.out.printf("item: %s\n", item);
        System.out.printf("error: %b\n", bindings.hasErrors());

        System.out.printf(">>> @RequestParam: %s\n", username);
        System.out.printf(">>> @RequestBody: %s\n", body);

        ModelAndView mav = new ModelAndView("cart");

        // List<Item> cart = new LinkedList<>(); // cannot move it in bc it dosent
        // remember - http is stateless protocol - sessions come in

        System.out.printf("item: %s\n", item);
        System.out.printf("error: %b\n", bindings.hasErrors());

        if (bindings.hasErrors()) {
            mav.setStatus(HttpStatusCode.valueOf(400));
            return mav;
        }

        List<Item> cart = Utils.getCart(sess); // only if theres no error, pass in a new session and get cart
        // User user = new User();
        cart.add(item);

        mav.addObject(ATTR_ITEM, new Item()); // clears item after entering and clicking add to cart
        mav.addObject(ATTR_CART, cart);
        mav.addObject(ATTR_USERNAME, username);

        mav.setStatus(HttpStatusCode.valueOf(200));
        return mav;
    }

}
