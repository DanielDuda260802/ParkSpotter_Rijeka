package com.example.parkspotter_rijeka;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CheckUsernamePasswordTest {
    private DBhelper dbHelper;
    private SQLiteDatabase database;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        dbHelper = new DBhelper(context);
        database = dbHelper.getWritableDatabase();
    }

    @After
    public void tearDown() {
        database.close();
    }

    @Test
    public void testCheckUsernamePassword_CorrectCredentials() {
        // Dodavanje test korisnika u bazu podataka
        dbHelper.insertData("test@example.com", "testUser", "password123");

        // Provjera vraća li funkcija checkUsernamePassword true za ispravne podatke
        assertTrue(dbHelper.checkUsernamePassword("testUser", "password123"));
    }

    @Test
    public void testCheckUsernamePassword_IncorrectUsername() {
        // Dodavanje test korisnika u bazu podataka
        dbHelper.insertData("test@example.com", "testUser", "password123");

        // Provjera vraća li funkcija checkUsernamePassword false za netočno korisničko ime
        assertFalse(dbHelper.checkUsernamePassword("nonExistingUser", "password123"));
    }

    @Test
    public void testCheckUsernamePassword_IncorrectPassword() {
        // Dodavanje test korisnika u bazu podataka
        dbHelper.insertData("test@example.com", "testUser", "password123");

        // Provjera vraća li funkcija checkUsernamePassword false za netočnu lozinku
        assertFalse(dbHelper.checkUsernamePassword("testUser", "wrongPassword"));
    }
}
