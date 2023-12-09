package ssf.iss.day15class.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ssf.iss.day15class.Utils;
import ssf.iss.day15class.models.Item;

@Repository
public class CartRepository {

    @Autowired @Qualifier(Utils.BEAN_REDIS)
    private RedisTemplate<String, String> template; 

    public boolean hasCart(String name){
        return template.hasKey(name);
    }

    public void deleteCart (String name){
        template.delete(name);
    }

    public void addCart (String name, List<Item> cart){
        ListOperations<String,String> list = template.opsForList();
        cart.stream()
            .forEach(item ->{
                String record ="%s,%d".formatted(item.getName(), item.getQuantity());
                list.leftPush(name, record);
        });
        
    }

    public List<Item> getCart(String name){
        ListOperations<String,String> list = template.opsForList();
        Long size = list.size(name);
        List<Item> cart = new LinkedList<>();

        for (String i:list.range(name, 0, size)){
            String[] terms = i.split(",");
            Item item = new Item();
            item.setName(terms[Utils.FIELD_NAME]);
            item.setQuantity(Integer.parseInt(terms[Utils.FIELD_QUANTITY]));
            cart.add(item);
        }

        return cart;
    }
}

//returns has operations
        // name of map, key of map, value of key
        // HashOperations<String, String, String> hashOps = template.opsForHash();

        //key of map, value of key - name (string), quantity(integer)
        //if use map, only can store 1 item but cart can store multiple items
        // use list - item data stored as (name, qty)
        // Map<String, String> values = hashOps.entries(name);

        // Item item = new Item();
        // item.setName(values.get(Utils.FIELD_NAME));
        // item.setQuantity(Integer.parseInt(values.get(Utils.FIELD_QUANTITY)));
