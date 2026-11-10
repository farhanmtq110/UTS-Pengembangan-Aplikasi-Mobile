package lat.pam.kopidannyemil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etEmail = findViewById(R.id.etLoginEmail);
        EditText etPassword = findViewById(R.id.etLoginPassword);
        Button btnLoginSubmit = findViewById(R.id.btnLoginSubmit);
        TextView tvForgotPassword = findViewById(R.id.tvForgotPassword);
        TextView tvGoToRegister = findViewById(R.id.tvGoToRegister);

        if (tvForgotPassword != null) {
            tvForgotPassword.setOnClickListener(v -> {
                Toast.makeText(LoginActivity.this, "Fitur instruksi Lupa Password telah dikirim ke Email.", Toast.LENGTH_SHORT).show();
            });
        }

        if (tvGoToRegister != null) {
            tvGoToRegister.setOnClickListener(v -> {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            });
        }

        if (btnLoginSubmit != null) {
            btnLoginSubmit.setOnClickListener(v -> {
                String email = etEmail != null ? etEmail.getText().toString().trim() : "";
                String password = etPassword != null ? etPassword.getText().toString().trim() : "";

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Email dan Password harus diisi!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}