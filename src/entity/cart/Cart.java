package entity.cart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.media.Media;
import entity.order.Order;
import entity.order.OrderMedia;

public class Cart {
    
    private List<CartMedia> lstCartMedia;
    private static Cart cartInstance;

    public static Cart getCart(){
        if(cartInstance == null) cartInstance = new Cart();
        return cartInstance;
    }

    private Cart(){
        lstCartMedia = new ArrayList<>();
    }

    public void addCartMedia(CartMedia cm){
        lstCartMedia.add(cm);
    }

    public void removeCartMedia(CartMedia cm){
        lstCartMedia.remove(cm);
    }

    public List getListMedia(){
        return lstCartMedia;
    }

    // This function is the replacement for the clear cat function in payment class
    public void emptyCart(){
        lstCartMedia.clear();
    }

}
