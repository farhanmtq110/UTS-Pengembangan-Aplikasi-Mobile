package lat.pam.kopidannyemil;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CartManager {
    private static CartManager instance;
    private Map<String, CartItem> itemsMap = new LinkedHashMap<>();
    private static final String PREF_NAME = "KopiDanNyemilPrefs";
    private static final String KEY_COMPLETED_ORDERS = "completed_orders_count";
    private static final String KEY_USER_NAME = "user_full_name";
    private static final String KEY_SAVED_COUNT = "saved_items_count";

    private List<CartItem> lastOrderItems = new ArrayList<>();
    private int lastSubtotal = 0;
    private int lastShippingFee = 0;
    private int lastTotal = 0;
    private String lastPaymentMethod = "Cash on Delivery (COD)";

    private CartManager() {}

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addItem(String id, String name, int price, int imageResId) {
        if (itemsMap.containsKey(id)) {
            CartItem item = itemsMap.get(id);
            item.setQuantity(item.getQuantity() + 1);
        } else {
            itemsMap.put(id, new CartItem(id, name, price, 1, imageResId));
        }
    }

    public void decreaseItem(String id) {
        if (itemsMap.containsKey(id)) {
            CartItem item = itemsMap.get(id);
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
            } else {
                itemsMap.remove(id);
            }
        }
    }

    public void removeItem(String id) {
        itemsMap.remove(id);
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(itemsMap.values());
    }

    public int getItemQuantity(String id) {
        if (itemsMap.containsKey(id)) {
            return itemsMap.get(id).getQuantity();
        }
        return 0;
    }

    public int getTotalItemCount() {
        int count = 0;
        for (CartItem item : itemsMap.values()) {
            count += item.getQuantity();
        }
        return count;
    }

    public int getSubtotal() {
        int subtotal = 0;
        for (CartItem item : itemsMap.values()) {
            subtotal += item.getPrice() * item.getQuantity();
        }
        return subtotal;
    }

    public int getShippingFee() {
        return itemsMap.isEmpty() ? 0 : 6000;
    }

    public int getTotal() {
        return getSubtotal() + getShippingFee();
    }

    public void clearCart() {
        itemsMap.clear();
    }

    public void recordCheckout(Context context, String paymentMethod) {
        lastOrderItems = new ArrayList<>(itemsMap.values());
        lastSubtotal = getSubtotal();
        lastShippingFee = getShippingFee();
        lastTotal = getTotal();
        lastPaymentMethod = paymentMethod;

        incrementCompletedOrdersCount(context);
        clearCart();
    }

    public List<CartItem> getLastOrderItems() {
        return lastOrderItems;
    }

    public int getLastSubtotal() { return lastSubtotal; }
    public int getLastShippingFee() { return lastShippingFee; }
    public int getLastTotal() { return lastTotal; }
    public String getLastPaymentMethod() { return lastPaymentMethod; }

    public int getCompletedOrdersCount(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_COMPLETED_ORDERS, 0);
    }

    public void incrementCompletedOrdersCount(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int current = prefs.getInt(KEY_COMPLETED_ORDERS, 0);
        prefs.edit().putInt(KEY_COMPLETED_ORDERS, current + 1).apply();
    }

    public String getUserName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_USER_NAME, "User");
    }

    public void setUserName(Context context, String name) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_USER_NAME, name).apply();
    }

    public int getSavedCount(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_SAVED_COUNT, 3);
    }
}