package com.example.parkspotter_rijeka;

import android.content.Intent;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class RegistrationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        EditText username, email, password, repassword;
        Button loginButton, registrationButton;

        username = findViewById(R.id.usernameNovi);
        email = findViewById(R.id.emailNovi);
        password = findViewById(R.id.passwordNovi);
        repassword = findViewById(R.id.repassword);
        loginButton = findViewById(R.id.LoginBtn);
        registrationButton = findViewById(R.id.RegistrationBtn);

        DBhelper myDB = new DBhelper(this);

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if(user.equals("") || pass.equals("") || repass.equals("")){
                    Toast.makeText(RegistrationActivity.this, "Potrebno je popuniti sva polja.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(pass.equals(repass)){
                        Boolean usercheckResult = myDB.checkUsername(user);
                        if(usercheckResult == false) {
                            Boolean regResult = myDB.insertData(user, pass);
                            if(regResult == true){
                                Toast.makeText(RegistrationActivity.this, "Uspješna registracija.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(RegistrationActivity.this, "Neuspješna registracija", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(RegistrationActivity.this, "Korisnik već postoji.\n Pokušajte ponovno.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                    Toast.makeText(RegistrationActivity.this, "Lozinke se ne podudaraju.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
