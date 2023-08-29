package com.example.parkspotter_rijeka;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetActivity extends AppCompatActivity {

    TextView username;
    EditText password, repassword;
    Button confirm_btn;

    DBhelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_activity);

        username = findViewById(R.id.username_reset_text);
        password = findViewById(R.id.password_reset);
        repassword = findViewById(R.id.repassword_reset);
        confirm_btn = findViewById(R.id.reset_confirm);

        DB = new DBhelper(this);

        Intent intent = getIntent();
        username.setText(intent.getStringExtra("username"));

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if (pass.equals(repass)) {

                    Boolean checkpasswordupdate = DB.updatePassword(user, pass);
                    if (checkpasswordupdate == true) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(ResetActivity.this, "Lozinka uspješno promijenjena.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ResetActivity.this, "Lozinka nije promijenjenja.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(ResetActivity.this, "Lozinke se ne podudaraju.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
