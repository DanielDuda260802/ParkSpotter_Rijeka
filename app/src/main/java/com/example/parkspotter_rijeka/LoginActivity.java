package com.example.parkspotter_rijeka;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView username;
    private AutoCompleteTextView password;
    private CheckBox rememberMeCheckBox;
    private TextView forgot;
    private SharedPreferences mPrefs;
    private static final String PREFS_NAME = "PrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        rememberMeCheckBox = findViewById(R.id.remember_me_chkb);
        forgot = findViewById(R.id.forgotPassword);
        Button loginButton = findViewById(R.id.loginButton);
        Button registrationButton = findViewById(R.id.RegistrationButton);


        setupAutoCompleteTextView();

        getPreferencesData();

        DBhelper myDB = new DBhelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(LoginActivity.this, "Unesite podatke za prijavu.", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean result = myDB.checkUsernamePassword(user, pass);
                    if (result) {
                        if (rememberMeCheckBox.isChecked()) {
                            SharedPreferences.Editor editor = mPrefs.edit();
                            editor.putString("pref_name_" + user, user);
                            editor.putString("pref_pass_" + user, pass);
                            editor.putBoolean("pref_check", true);
                            editor.apply();
                        } else {
                            mPrefs.edit().clear().apply();
                        }
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Neispravni podaci za prijavu.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        username.setOnItemClickListener((parent, view, position, id) -> {
            String selectedUsername = parent.getItemAtPosition(position).toString();
            String storedPassword = getStoredPassword(selectedUsername);
            if (storedPassword != null) {
                password.setText(storedPassword);
            } else {
                password.setText("");
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupAutoCompleteTextView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, getStoredUsernames());

        username.setAdapter(adapter);
    }

    private List<String> getStoredUsernames() {
        List<String> usernames = new ArrayList<>();
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Map<String, ?> allPrefs = sp.getAll();

        for (Map.Entry<String, ?> entry : allPrefs.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith("pref_name_")) {
                usernames.add(entry.getValue().toString());
            }
        }

        return usernames;
    }

    private String getStoredPassword(String username) {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return sp.getString("pref_pass_" + username, null);
    }

    private void getPreferencesData() {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (sp.contains("pref_name")) {
            String u = sp.getString("pref_name", "not found");
            username.setText(u);

            if (sp.contains("pref_pass_" + u)) {
                String p = sp.getString("pref_pass_" + u, "");
                password.setText(p);
            }
        }

        if (sp.contains("pref_check")) {
            Boolean b = sp.getBoolean("pref_check", false);
            rememberMeCheckBox.setChecked(b);
        }
    }
}


