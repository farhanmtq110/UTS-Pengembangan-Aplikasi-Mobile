package lat.pam.kopidannyemil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrderActivity extends AppCompatActivity {

    private LinearLayout llOrderItemsContainer;
    private TextView tvEmptyCart;
    private TextView tvSubtotal;
    private TextView tvShippingFee;
    private TextView tvTotal;
    private Button btnCheckoutNow;
    private ImageView ivBackHome;
    private RadioGroup rgPaymentMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        llOrderItemsContainer = findViewById(R.id.llOrderItemsContainer);
        tvEmptyCart = findViewById(R.id.tvEmptyCart);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvShippingFee = findViewById(R.id.tvShippingFee);
        tvTotal = findViewById(R.id.tvTotal);
        btnCheckoutNow = findViewById(R.id.btnCheckoutNow);
        ivBackHome = findViewById(R.id.ivBackHome);
        rgPaymentMethod = findViewById(R.id.rgPaymentMethod);

        if (ivBackHome != null) {
            ivBackHome.setOnClickListener(v -> finish());
        }

        if (btnCheckoutNow != null) {
            btnCheckoutNow.setOnClickListener(v -> {
                if (CartManager.getInstance().getCartItems().isEmpty()) {
                    Toast.makeText(OrderActivity.this, "Keranjang masih kosong! Silakan pilih menu terlebih dahulu.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String selectedPayment = "Cash on Delivery (COD)";
                if (rgPaymentMethod != null) {
                    int selectedId = rgPaymentMethod.getCheckedRadioButtonId();
                    RadioButton rb = findViewById(selectedId);
                    if (rb != null) {
                        selectedPayment = rb.getText().toString();
                    }
                }

                CartManager.getInstance().recordCheckout(OrderActivity.this, selectedPayment);

                Intent intent = new Intent(OrderActivity.this, ConfirmationActivity.class);
                startActivity(intent);
                finish();
            });
        }

        renderCart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        renderCart();
    }

    private void renderCart() {
        if (llOrderItemsContainer == null) return;
        llOrderItemsContainer.removeAllViews();

        List<CartItem> cartItems = CartManager.getInstance().getCartItems();

        if (cartItems.isEmpty()) {
            if (tvEmptyCart != null) tvEmptyCart.setVisibility(View.VISIBLE);
        } else {
            if (tvEmptyCart != null) tvEmptyCart.setVisibility(View.GONE);

            for (int i = 0; i < cartItems.size(); i++) {
                CartItem item = cartItems.get(i);
                View itemView = createCartItemView(item);
                llOrderItemsContainer.addView(itemView);

                if (i < cartItems.size() - 1) {
                    View divider = new View(this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, 2);
                    params.setMargins(0, 16, 0, 16);
                    divider.setLayoutParams(params);
                    divider.setBackgroundColor(Color.parseColor("#EFEFEF"));
                    llOrderItemsContainer.addView(divider);
                }
            }
        }

        updateTotals();
    }

    private View createCartItemView(CartItem item) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        ImageView iv = new ImageView(this);
        int imgSize = (int) (60 * getResources().getDisplayMetrics().density);
        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(imgSize, imgSize);
        iv.setLayoutParams(imgParams);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setImageResource(item.getImageResId());
        row.addView(iv);

        LinearLayout infoLayout = new LinearLayout(this);
        infoLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams infoParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        infoParams.setMargins((int) (12 * getResources().getDisplayMetrics().density), 0, 0, 0);
        infoLayout.setLayoutParams(infoParams);

        TextView tvName = new TextView(this);
        tvName.setText(item.getName());
        tvName.setTextColor(Color.parseColor("#0F3D3A"));
        tvName.setTextSize(13);
        tvName.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView tvPrice = new TextView(this);
        tvPrice.setText(formatRupiah(item.getPrice()));
        tvPrice.setTextColor(Color.parseColor("#6C151E"));
        tvPrice.setTextSize(12);
        tvPrice.setTypeface(null, android.graphics.Typeface.BOLD);

        infoLayout.addView(tvName);
        infoLayout.addView(tvPrice);
        row.addView(infoLayout);

        LinearLayout qtyLayout = new LinearLayout(this);
        qtyLayout.setOrientation(LinearLayout.HORIZONTAL);
        qtyLayout.setGravity(Gravity.CENTER_VERTICAL);

        Button btnMinus = new Button(this);
        int btnSize = (int) (32 * getResources().getDisplayMetrics().density);
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(btnSize, btnSize);
        btnMinus.setLayoutParams(btnParams);
        btnMinus.setText("-");
        btnMinus.setTextColor(Color.parseColor("#0F3D3A"));
        btnMinus.setBackgroundResource(R.drawable.rounded_social_button);
        btnMinus.setPadding(0, 0, 0, 0);
        btnMinus.setOnClickListener(v -> {
            CartManager.getInstance().decreaseItem(item.getId());
            renderCart();
        });

        TextView tvQty = new TextView(this);
        int tvQtyWidth = (int) (28 * getResources().getDisplayMetrics().density);
        LinearLayout.LayoutParams qtyParams = new LinearLayout.LayoutParams(tvQtyWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
        tvQty.setLayoutParams(qtyParams);
        tvQty.setGravity(Gravity.CENTER);
        tvQty.setText(String.valueOf(item.getQuantity()));
        tvQty.setTextColor(Color.parseColor("#0F3D3A"));
        tvQty.setTextSize(14);
        tvQty.setTypeface(null, android.graphics.Typeface.BOLD);

        Button btnPlus = new Button(this);
        btnPlus.setLayoutParams(btnParams);
        btnPlus.setText("+");
        btnPlus.setTextColor(Color.parseColor("#F5DABF"));
        btnPlus.setBackgroundResource(R.drawable.rounded_button);
        btnPlus.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#6C151E")));
        btnPlus.setPadding(0, 0, 0, 0);
        btnPlus.setOnClickListener(v -> {
            CartManager.getInstance().addItem(item.getId(), item.getName(), item.getPrice(), item.getImageResId());
            renderCart();
        });

        qtyLayout.addView(btnMinus);
        qtyLayout.addView(tvQty);
        qtyLayout.addView(btnPlus);
        row.addView(qtyLayout);

        return row;
    }

    private void updateTotals() {
        int subtotal = CartManager.getInstance().getSubtotal();
        int shippingFee = CartManager.getInstance().getShippingFee();
        int total = CartManager.getInstance().getTotal();

        if (tvSubtotal != null) tvSubtotal.setText(formatRupiah(subtotal));
        if (tvShippingFee != null) tvShippingFee.setText(formatRupiah(shippingFee));
        if (tvTotal != null) tvTotal.setText(formatRupiah(total));
    }

    private String formatRupiah(int amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        return format.format(amount).replace(",00", "").replace("Rp", "Rp ");
    }
}