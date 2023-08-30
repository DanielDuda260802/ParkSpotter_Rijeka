package com.example.parkspotter_rijeka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {

    EditText username, email;
    Button reset;
    DBhelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_activity);

        username = findViewById(R.id.username_reset);
        email = findViewById(R.id.email_reset);
        reset = findViewById(R.id.Reset_Btn);

        DB = new DBhelper(this);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String Email = email.getText().toString();

                Boolean checkuser = DB.checkUsernameEmail(user, Email);
                if(checkuser==true){
                    Intent intent = new Intent(PasswordActivity.this, ResetActivity.class);
                    intent.putExtra("username", user);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(PasswordActivity.this, "Korisnik ne postoji", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
