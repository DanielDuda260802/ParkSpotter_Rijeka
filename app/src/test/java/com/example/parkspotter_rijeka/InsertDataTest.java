package com.example.parkspotter_rijeka;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class InsertDataTest {

        private DBhelper dbHelper;
        private SQLiteDatabase testDB;

        @Before
        public void setup() {
            Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
            dbHelper = new DBhelper(context);
            testDB = dbHelper.getWritableDatabase();
        }

        @After
        public void cleanup() {
            dbHelper.close();
        }

        @Test
        public void testInsertData_Successful() {
            // Priprema podataka za unos
            String email = "test@example.com";
            String username = "testuser";
            String password = "testpassword";

            // Poziv funkcije za unos
            boolean result = dbHelper.insertData(email, username, password);

            // Provjera rezultata
            assertTrue(result);

            // Provjera je li unos zapravo izvršen u bazi podataka
            String[] columns = {"email"};
            String selection = "username = ?";
            String[] selectionArgs = {username};
            Cursor cursor = testDB.query("users", columns, selection, selectionArgs, null, null, null);
            assertTrue(cursor.moveToFirst());
            assertEquals(email, cursor.getString(cursor.getColumnIndex("email")));
            cursor.close();
        }

        @Test
        public void testInsertData_Failure() {
            // Pokušaj ponovnog unosa istog korisničkog imena
            String email = "test@example.com";
            String username = "testuser";
            String password = "testpassword";

            // Prvi unos
            boolean firstInsertResult = dbHelper.insertData(email, username, password);
            assertTrue(firstInsertResult);

            // Pokušaj ponovnog unosa istih podataka
            boolean secondInsertResult = dbHelper.insertData(email, username, password);

            // Drugi unos ne bi smio uspjeti
            assertFalse(secondInsertResult);
        }
    }
