package ssf.iss.d13workshop.controller;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import ssf.iss.d13workshop.model.Contact;
import ssf.iss.d13workshop.repo.ContactRepo;

// @Controller
// @RequestMapping("/book")
public class AddressBookController {
    @Autowired
    ContactRepo contactRepo;


    // @GetMapping("/contact") // http://localhost:8080/book/contact
    public String contactForm(Model model) {
        Contact contact = new Contact();

        model.addAttribute("contact",contact);
        return "contactform";
    }

    // @PostMapping("/savecontact")
    public String saveContact(@Valid @ModelAttribute("contact") Contact conForm,
            BindingResult result, Model model) throws FileNotFoundException{
            
        // String valid = conForm.validAge();
        // if(valid !=null){
        //     result.addError(new ObjectError("DoB", valid));
        // }

        if (result.hasErrors()){
            System.out.println(result.getAllErrors());  
            return "contactForm";
        }

        contactRepo.save(conForm);
        model.addAttribute("newcontact", conForm);
        return "created";
    }

    // @GetMapping("/contact/{id}")
    public String showContact(@PathVariable String id, Model model){
        Contact contact = new Contact();
        contact = contactRepo.openFileId(id);
        model.addAttribute("contact", contact);

        if (contact == null) {
            return "error";
        }
        return "displaycontent";
    }

    // @GetMapping ("/list")
    public String allContacts(Model model) {
        model.addAttribute("contacts", contactRepo.listContacts());
        return "contacts";
    }  


}
