package lat.pam.kopidannyemil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvGreetingName;
    private TextView tvOrderCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button btnNavHome = findViewById(R.id.btnNavHome);
        Button btnNavOrder = findViewById(R.id.btnNavOrder);
        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnSaveProfile = findViewById(R.id.btnSaveProfile);
        EditText etProfileName = findViewById(R.id.etProfileName);
        tvGreetingName = findViewById(R.id.tvGreetingName);
        tvOrderCount = findViewById(R.id.tvOrderCount);

        updateProfileData();

        if (btnNavHome != null) {
            btnNavHome.setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            });
        }

        if (btnNavOrder != null) {
            btnNavOrder.setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, OrderActivity.class);
                startActivity(intent);
                finish();
            });
        }

        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, SignUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            });
        }

        if (btnSaveProfile != null) {
            btnSaveProfile.setOnClickListener(v -> {
                String newName = etProfileName != null ? etProfileName.getText().toString().trim() : "";
                if (!newName.isEmpty()) {
                    CartManager.getInstance().setUserName(ProfileActivity.this, newName);
                    updateProfileData();
                    if (etProfileName != null) etProfileName.setText("");
                    Toast.makeText(ProfileActivity.this, "Nama pengguna berhasil diperbarui!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfileActivity.this, "Silakan masukkan nama pengguna!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateProfileData();
    }

    private void updateProfileData() {
        String name = CartManager.getInstance().getUserName(this);
        if (tvGreetingName != null) {
            tvGreetingName.setText("Hi, " + name + " 👋");
        }

        int count = CartManager.getInstance().getCompletedOrdersCount(this);
        if (tvOrderCount != null) {
            tvOrderCount.setText(String.valueOf(count));
        }
    }
}