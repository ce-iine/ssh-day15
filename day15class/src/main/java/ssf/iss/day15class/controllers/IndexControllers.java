package ssf.iss.day15class.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ssf.iss.day15class.models.Item;

// @Controller
// @RequestMapping ("/index.html")
public class IndexControllers {

    // @GetMapping
    public String getIndex(Model model){
        model.addAttribute("item", new Item());
        return "cart";
    }

    
    
}
