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
public class CheckUsernameEmailTest {
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
    public void testCheckUsernameEmail_CorrectCredentials() {
        // Dodavanje test korisnika u bazu podataka
        dbHelper.insertData("test@example.com", "testUser", "password123");

        // Provjera vraća li funkcija checkUsernameEmail vraća true za točne podatke
        assertTrue(dbHelper.checkUsernameEmail("testUser", "test@example.com"));
    }

    @Test
    public void testCheckUsernameEmail_IncorrectUsername() {
        // Dodavanje test korisnika u bazu podataka
        dbHelper.insertData("test@example.com", "testUser", "password123");

        // Provjera vraća li funkcija checkUsernameEmail false za netočno korisničko ime
        assertFalse(dbHelper.checkUsernameEmail("nonExistingUser", "test@example.com"));
    }

    @Test
    public void testCheckUsernameEmail_IncorrectEmail() {
        // Dodavanje test korisnika u bazu podataka
        dbHelper.insertData("test@example.com", "testUser", "password123");

        // Provjera vraća li funkcija checkUsernameEmail vraća false za netočnu email adresu
        assertFalse(dbHelper.checkUsernameEmail("testUser", "wrong@example.com"));
    }
}
