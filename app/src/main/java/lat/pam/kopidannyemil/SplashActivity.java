package lat.pam.kopidannyemil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Button btnStart = findViewById(R.id.btnStartNow);
        if (btnStart != null) {
            btnStart.setOnClickListener(v -> {
                Intent intent = new Intent(SplashActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }
}