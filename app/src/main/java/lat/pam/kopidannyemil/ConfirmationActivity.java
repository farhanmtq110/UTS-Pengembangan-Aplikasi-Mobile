package lat.pam.kopidannyemil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        LinearLayout llReceiptItemsContainer = findViewById(R.id.llReceiptItemsContainer);
        TextView tvReceiptSubtotal = findViewById(R.id.tvReceiptSubtotal);
        TextView tvReceiptShipping = findViewById(R.id.tvReceiptShipping);
        TextView tvReceiptTotal = findViewById(R.id.tvReceiptTotal);
        TextView tvReceiptPaymentMethod = findViewById(R.id.tvReceiptPaymentMethod);
        TextView tvReceiptEstimation = findViewById(R.id.tvReceiptEstimation);
        Button btnBackToHome = findViewById(R.id.btnBackToHome);

        if (llReceiptItemsContainer != null) {
            llReceiptItemsContainer.removeAllViews();
            List<CartItem> items = CartManager.getInstance().getLastOrderItems();

            if (items.isEmpty()) {
                TextView tvEmpty = new TextView(this);
                tvEmpty.setText("Tidak ada rincian item.");
                tvEmpty.setTextColor(Color.parseColor("#8C9A99"));
                tvEmpty.setTextSize(12);
                llReceiptItemsContainer.addView(tvEmpty);
            } else {
                for (CartItem item : items) {
                    LinearLayout row = new LinearLayout(this);
                    row.setOrientation(LinearLayout.HORIZONTAL);
                    row.setGravity(Gravity.CENTER_VERTICAL);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, 4, 0, 4);
                    row.setLayoutParams(params);

                    TextView tvNameQty = new TextView(this);
                    tvNameQty.setText(item.getName() + " x" + item.getQuantity());
                    tvNameQty.setTextColor(Color.parseColor("#0F3D3A"));
                    tvNameQty.setTextSize(12);
                    LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(
                            0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                    tvNameQty.setLayoutParams(nameParams);

                    TextView tvItemPrice = new TextView(this);
                    tvItemPrice.setText(formatRupiah(item.getPrice() * item.getQuantity()));
                    tvItemPrice.setTextColor(Color.parseColor("#0F3D3A"));
                    tvItemPrice.setTextSize(12);
                    tvItemPrice.setTypeface(null, android.graphics.Typeface.BOLD);

                    row.addView(tvNameQty);
                    row.addView(tvItemPrice);
                    llReceiptItemsContainer.addView(row);
                }
            }
        }

        if (tvReceiptSubtotal != null) {
            tvReceiptSubtotal.setText(formatRupiah(CartManager.getInstance().getLastSubtotal()));
        }
        if (tvReceiptShipping != null) {
            tvReceiptShipping.setText(formatRupiah(CartManager.getInstance().getLastShippingFee()));
        }
        if (tvReceiptTotal != null) {
            tvReceiptTotal.setText(formatRupiah(CartManager.getInstance().getLastTotal()));
        }

        if (tvReceiptPaymentMethod != null) {
            tvReceiptPaymentMethod.setText(CartManager.getInstance().getLastPaymentMethod());
        }

        if (tvReceiptEstimation != null) {
            tvReceiptEstimation.setText("15 - 25 Menit");
        }

        if (btnBackToHome != null) {
            btnBackToHome.setOnClickListener(v -> {
                Intent intent = new Intent(ConfirmationActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            });
        }
    }

    private String formatRupiah(int amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        return format.format(amount).replace(",00", "").replace("Rp", "Rp ");
    }
}