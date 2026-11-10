package lat.pam.kopidannyemil;

public class CartItem {
    private String id;
    private String name;
    private int price;
    private int quantity;
    private int imageResId;

    public CartItem(String id, String name, int price, int quantity, int imageResId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageResId = imageResId;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public int getImageResId() { return imageResId; }
}