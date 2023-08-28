package com.example.parkspotter_rijeka;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login); // Postavite layout za login.xml

        // Pronađite gumb "Login" u layoutu
        Button loginButton = findViewById(R.id.loginButton);

        // Postavite slušača klika na gumb "Login"
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simulirajte provjeru korisničkog unosa i uspješnu prijavu
                boolean uspjesnaPrijava = true;

                if (uspjesnaPrijava) {
                    // Ako je prijava uspješna, prebaci se na MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Zatvori LoginActivity kako se ne bi moglo vratiti natrag
                } else {
                    // Ovdje možete dodati reakciju na neuspješnu prijavu
                }
            }
        });
    }
}
