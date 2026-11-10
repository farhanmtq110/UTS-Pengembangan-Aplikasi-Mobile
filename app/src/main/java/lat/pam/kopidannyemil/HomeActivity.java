package lat.pam.kopidannyemil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HomeActivity extends AppCompatActivity {

    private Button btnNavOrder;
    private Button btnNavProfile;
    private ImageView ivCartIcon;

    private static class MenuData {
        String id;
        String name;
        int price;
        int imageResId;
        int cardId;

        MenuData(String id, String name, int price, int imageResId, int cardId) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.imageResId = imageResId;
            this.cardId = cardId;
        }
    }

    private final MenuData[] menuList = {
        new MenuData("1", "Kopi Susu Gula Aren", 22000, R.drawable.kopi_gula_aren, R.id.cardMenu1),
        new MenuData("2", "Donut Coklat", 12000, R.drawable.donat_coklat, R.id.cardMenu2),
        new MenuData("3", "Cappuccino", 23000, R.drawable.cappucino, R.id.cardMenu3),
        new MenuData("4", "Matcha Latte", 25000, R.drawable.matcha_latte, R.id.cardMenu4),
        new MenuData("5", "Croissant Keju", 18000, R.drawable.croissant_keju, R.id.cardMenu5),
        new MenuData("6", "French Fries", 15000, R.drawable.french_fries, R.id.cardMenu6),
        new MenuData("7", "Americano", 18000, R.drawable.americano, R.id.cardMenu7),
        new MenuData("8", "Toast Bakar Keju", 16000, R.drawable.toast_bakar_keju, R.id.cardMenu8)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnNavOrder = findViewById(R.id.btnNavOrder);
        btnNavProfile = findViewById(R.id.btnNavProfile);
        ivCartIcon = findViewById(R.id.ivCartIcon);

        updateCartBadge();

        if (btnNavOrder != null) {
            btnNavOrder.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, OrderActivity.class);
                startActivity(intent);
            });
        }

        if (btnNavProfile != null) {
            btnNavProfile.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            });
        }

        if (ivCartIcon != null) {
            ivCartIcon.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, OrderActivity.class);
                startActivity(intent);
            });
        }

        for (MenuData menu : menuList) {
            CardView card = findViewById(menu.cardId);
            if (card != null) {
                card.setOnClickListener(v -> {
                    CartManager.getInstance().addItem(menu.id, menu.name, menu.price, menu.imageResId);
                    int currentQty = CartManager.getInstance().getItemQuantity(menu.id);
                    Toast.makeText(HomeActivity.this, menu.name + " ditambahkan! (Jumlah: " + currentQty + ")", Toast.LENGTH_SHORT).show();
                    updateCartBadge();
                });
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartBadge();
    }

    private void updateCartBadge() {
        int totalItems = CartManager.getInstance().getTotalItemCount();
        if (btnNavOrder != null) {
            if (totalItems > 0) {
                btnNavOrder.setText("Order (" + totalItems + ")");
            } else {
                btnNavOrder.setText("Order");
            }
        }
    }
}