package controller;
import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.media.Media;
import java.util.List;
public class BaseController {
    public BaseController() {
    }
    public CartMedia checkMediaInCart(Media media) {
        return Cart.getCart().checkMediaInCart(media);
    }

    public List getListCartMedia() {
        return Cart.getCart().getListMedia();
    }
}
