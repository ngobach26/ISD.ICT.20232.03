package entity.order;

import entity.media.Media;

import java.util.logging.Logger;

public class OrderMedia {

    private static final Logger LOGGER = utils.LOGGER.getLogger(OrderMedia.class.getName());
    private Media media;
    private int price;
    private int quantity;

    public OrderMedia(Media media, int quantity, int price) {
        this.media = media;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderMedia() {

    }

    @Override
    public String toString() {
        return "{" +
                "  media='" + media + "'" +
                ", quantity='" + quantity + "'" +
                ", price='" + price + "'" +
                "}";
    }

    public Media getMedia() {
        return this.media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int calculatePrice() {
        return getMedia().getPrice() * getQuantity();
    }

}
