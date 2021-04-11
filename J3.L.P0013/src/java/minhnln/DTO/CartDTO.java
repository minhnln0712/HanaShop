/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhnln.DTO;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Welcome
 */
public class CartDTO implements Serializable {

    private Map<String, ProductDTO> cart;

    public Map<String, ProductDTO> getCart() {
        return cart;
    }

    public boolean addToCart(ProductDTO dto) throws Exception {
        if (this.cart == null) {
            this.cart = new HashMap<>();
        }
        if (this.cart.containsKey(dto.getProductID())) {
            int quantitynow = this.cart.get(dto.getProductID()).getQuantity();
            if (quantitynow == this.cart.get(dto.getProductID()).getMaxquantity()) {
                return false;
            }
            this.cart.get(dto.getProductID()).setQuantity(quantitynow + 1);
            return true;
        } else {
            dto.setQuantity(1);
            this.cart.put(dto.getProductID(), dto);
            return true;
        }
    }

    public void remove(String id) throws Exception {
        if (this.cart.containsKey(id)) {
            this.cart.remove(id);
        }
    }

    public float getTotal() throws Exception {
        float result = 0;
        for (ProductDTO dto : this.cart.values()) {
            result += dto.getQuantity() * dto.getPrice();
        }
        return result;
    }

    public void changeQuantitybyID(String id, int newquantity) throws Exception {
        if (this.cart.containsKey(id)) {
            this.cart.get(id).setQuantity(newquantity);
        }
        if (this.cart.containsKey(id) && newquantity == 0) {
            remove(id);
        }
    }
}
