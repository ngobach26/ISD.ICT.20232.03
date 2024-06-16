package controller;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import DAO.MediaDAO;
import common.exception.MediaNotAvailableException;
import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.media.Media;
import services.DAOService.DAOFactory;
import services.DAOService.MediaService;
import utils.Utils;
import views.screen.home.MediaHandler;

/**
 * This class controls the flow of events in homescreen
 */
public class HomeController extends BaseController{

    private static final Logger LOGGER = Utils.getLogger(HomeController.class.getName());
    private final MediaDAO mediaService ;
    /**
     * this method gets all Media in DB and return back to home to display
     * @return List[Media]
     * @throws SQLException
     */
    public HomeController(){
        this.mediaService = DAOFactory.getMediaDAO();
    }
    public List getAllMedia() throws SQLException{
        return mediaService.getAllMedia();
    }

    public List<Media> search(String searchText) throws SQLException{

        return mediaService.searchMedia(searchText);
    }
    public void sortTitle(List<MediaHandler> list){
        Comparator<MediaHandler> mediaTitleComparator = new Comparator<MediaHandler>() {
            @Override
            public int compare(MediaHandler m1, MediaHandler m2) {
                return m1.getMedia().getTitle().compareToIgnoreCase(m2.getMedia().getTitle());
            }
        };
        list.sort(mediaTitleComparator);
    }
    public void sortPrice(List<MediaHandler> list) {
        Comparator<MediaHandler> mediaTitleComparator = new Comparator<MediaHandler>() {
            @Override
            public int compare(MediaHandler m1, MediaHandler m2) {
                int priceComparison = m1.getMedia().getPrice() - m2.getMedia().getPrice();
                return priceComparison;
            }
        };
        list.sort(mediaTitleComparator);
    }

    public void addMediaToCart(Media media, int quantity) throws MediaNotAvailableException, SQLException {
        if (quantity > media.getQuantity()) throw new MediaNotAvailableException();
        Cart cart = Cart.getCart();
        CartMedia mediaInCart = cart.checkMediaInCart(media);
        if (mediaInCart != null) {
            mediaInCart.setQuantity(mediaInCart.getQuantity() + quantity);
        } else {
            CartMedia cartMedia = new CartMedia(media, cart, quantity, media.getPrice());
            cart.getListMedia().add(cartMedia);
            LOGGER.info("Added " + cartMedia.getQuantity() + " " + media.getTitle() + " to cart");
        }

        media.setQuantity(media.getQuantity() - quantity);
    }

    public int checkMediaAvailability(Media media, int requestedQuantity) throws SQLException {
        return Math.min(requestedQuantity, media.getQuantity());
    }

}
