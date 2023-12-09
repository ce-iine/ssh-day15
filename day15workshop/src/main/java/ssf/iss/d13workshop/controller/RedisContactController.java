package ssf.iss.d13workshop.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import ssf.iss.d13workshop.model.Contact;
import ssf.iss.d13workshop.repo.ContactsRedis;

@Controller
@RequestMapping("/book")
public class RedisContactController {

    private Logger logger = Logger.getLogger(RedisContactController.class.getName());

    @Autowired 
    private ContactsRedis contactsRedis;

    @GetMapping("/contact")
    public String contactForm(Model model) {
        Contact contact = new Contact();
        model.addAttribute("contact",contact);
        return "contactform";
    }

    @PostMapping("/contact")
    public String saveContact(@Valid @ModelAttribute("contact") Contact conForm,
            BindingResult result, Model model) {

        conForm.setId();
        contactsRedis.save(conForm);
        System.out.printf("creating contact %s\n", conForm);

        logger.info("Contact: %s\n".formatted(conForm));

        model.addAttribute("newcontact", conForm);
        return "created";
    }


    @GetMapping("/contact/{id}")
    public String showContact(@PathVariable String id, Model model) throws Exception {

        model.addAttribute("contact", contactsRedis.getContact(id));
        return "displaycontent";
    }


    @GetMapping ("/list")
    public String allContacts(Model model) throws Exception {
        model.addAttribute("contacts", contactsRedis.allContacts());
        System.out.println(contactsRedis.allContacts());
        return "contacts";
    }  
    
}
