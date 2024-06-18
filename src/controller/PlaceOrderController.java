package controller;

import java.sql.SQLException;
import java.util.logging.Logger;

import DAO.IMediaDAO;
import common.exception.MediaNotAvailableException;
import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.invoice.Invoice;
import entity.order.DeliveryInformation;
import entity.order.Order;
import entity.order.OrderMedia;
import entity.user.User;
import services.DAOFactory;
import services.user.LoginManager;

public class PlaceOrderController extends BaseController{
    private static final Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());
    private final IMediaDAO mediaDAO;

    public PlaceOrderController() throws MediaNotAvailableException {
        this.mediaDAO = DAOFactory.getMediaDAO();
    }


    public void placeOrder() throws SQLException{
        User user = LoginManager.getSavedLoginInfo();
        Cart.getCart(user.getId()).checkAvailabilityOfProduct();
    }

    public Order createOrder() throws SQLException{
        Order order = new Order();
        User user = LoginManager.getSavedLoginInfo();
        for (Object object : Cart.getCart(user.getId()).getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(),
                    cartMedia.getQuantity(),
                    cartMedia.getPrice());
            orderMedia.setPrice(orderMedia.calculatePrice());
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    public Invoice createInvoice(DeliveryInformation deliveryInformation,Order order) {
        return new Invoice(deliveryInformation, order);
    }


}