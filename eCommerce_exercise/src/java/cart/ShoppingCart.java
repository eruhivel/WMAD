package cart;

import entity.Product;
import java.util.*;

/**
 *
 * @author juanluis
 */
public class ShoppingCart {

    List<ShoppingCartItem> cart;

    public ShoppingCart() {
        cart = new ArrayList<ShoppingCartItem>();
    }

    public synchronized void addItem(Product product) {
        ShoppingCartItem newItem = new ShoppingCartItem(product);
        for (ShoppingCartItem item : cart) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        cart.add(newItem);
    }

    public synchronized void update(Product product, String quantity) {
        for (ShoppingCartItem item : cart) {
            if (item.getProduct().getId().equals(product.getId())) {
                System.out.println(quantity);
                item.setQuantity(Integer.parseInt(quantity));
                return;
            }
        }
    }

    public synchronized List<ShoppingCartItem> getItems() {
        return cart;
    }

    public synchronized int getNumberOfItems() {
        int numberItems = 0;
        for (ShoppingCartItem item : cart) {
            numberItems += item.getQuantity();
        }
        return numberItems;
    }

    public synchronized double getTotal() {
        double totalPrice = 0;
        for (ShoppingCartItem item : cart) {
            totalPrice += item.getTotal();
        }
        return totalPrice;
    }

    public synchronized void clear() {
        cart.clear();
    }
}
