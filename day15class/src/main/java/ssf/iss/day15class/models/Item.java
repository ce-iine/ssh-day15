package ssf.iss.day15class.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class Item {

    //cannot be empty
    @NotEmpty (message="Please enter your nme")
    private String name;

    //btwn 1 and 10
    @Min (value = 1, message = "You must order at least 1 item")
    @Max (value = 10, message = "You cam order at most 10 item")
    private Integer quantity;


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item [name=" + name + ", quantity=" + quantity + "]";
    }
    
}
