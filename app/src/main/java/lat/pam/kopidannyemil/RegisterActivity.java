package lat.pam.kopidannyemil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText etRegName = findViewById(R.id.etRegName);
        EditText etRegEmail = findViewById(R.id.etRegEmail);
        EditText etRegPassword = findViewById(R.id.etRegPassword);
        Button btnSubmitRegister = findViewById(R.id.btnSubmitRegister);
        TextView tvBackToLogin = findViewById(R.id.tvBackToLogin);

        if (btnSubmitRegister != null) {
            btnSubmitRegister.setOnClickListener(v -> {
                String name = etRegName != null ? etRegName.getText().toString().trim() : "";
                String email = etRegEmail != null ? etRegEmail.getText().toString().trim() : "";
                String password = etRegPassword != null ? etRegPassword.getText().toString().trim() : "";

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Silakan lengkapi semua data!", Toast.LENGTH_SHORT).show();
                    return;
                }

                CartManager.getInstance().setUserName(RegisterActivity.this, name);

                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            });
        }

        if (tvBackToLogin != null) {
            tvBackToLogin.setOnClickListener(v -> {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }
}