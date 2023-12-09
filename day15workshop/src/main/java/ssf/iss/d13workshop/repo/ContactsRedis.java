package ssf.iss.d13workshop.repo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ssf.iss.d13workshop.model.Contact;


@Repository
public class ContactsRedis {

    @Autowired @Qualifier("ContactsRedis") // from AppConfig if you name a bean, go back to that bean 
	private RedisTemplate<String, Object> template;
    
    private List<Contact> contacts = new LinkedList<>();

    public void save(Contact contact) {
        contacts.add(contact);
        template.opsForValue().set(contact.getId(), contact.toJson().toString());
    }

    
    public Contact getContact (String id) throws Exception { //optional?

        Contact contact = new Contact();
        ObjectMapper mapper = new ObjectMapper(); // converts json string stored to Contact object 
        mapper.registerModule(new JavaTimeModule()); // to map DoB field 

        if (template.hasKey(id)){
            String json = template.opsForValue().get(id).toString();
            return mapper.readValue(json, Contact.class); // returns contact
        } 
        
        return contact;
    }

    public List<Contact> allContacts() throws Exception {
        Contact contact = new Contact();

        // Long size = template.size();

        List<Contact> allContacts = new ArrayList<Contact>();
        
        for (String key : template.opsForValue().getOperations().keys("*")){ //.getOperations is operations to get things
            System.out.println(key);
            contact = getContact(key);
            allContacts.add(contact);
        }

        return allContacts;
    }





    

}