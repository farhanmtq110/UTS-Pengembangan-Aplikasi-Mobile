package lat.pam.kopidannyemil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button btnToLogin = findViewById(R.id.btnGoToLogin);
        Button btnToRegister = findViewById(R.id.btnGoToRegister);

        if (btnToLogin != null) {
            btnToLogin.setOnClickListener(v -> {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            });
        }

        if (btnToRegister != null) {
            btnToRegister.setOnClickListener(v -> {
                Intent intent = new Intent(SignUpActivity.this, RegisterActivity.class);
                startActivity(intent);
            });
        }
    }
}