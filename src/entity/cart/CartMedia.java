package entity.cart;

import entity.media.Media;

public class CartMedia {
    private int cartId;
    private Media media;
    private int quantity;
    private int price;

    public CartMedia(){

    }

    public CartMedia(Media media, Cart cart, int quantity, int price) {
        this.media = media;
        this.quantity = quantity;
        this.price = price;
    }
    public CartMedia(int cartId, Media media, int quantity, int price) {
        this.cartId = cartId;
        this.media = media;
        this.quantity = quantity;
        this.price = price;
    }
//    public CartMedia(Media media, int quantity)
//    {
//        this.media = media;
//        this.quantity = quantity;
//    }

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

    @Override
    public String toString() {
        return "{"
                + " media='" + media + "'"
                + ", quantity='" + quantity + "'"
                + "}";
    }

}

