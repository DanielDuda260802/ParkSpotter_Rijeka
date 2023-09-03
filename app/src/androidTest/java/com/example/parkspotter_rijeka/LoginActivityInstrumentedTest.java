package com.example.parkspotter_rijeka;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityInstrumentedTest {
    private DBhelper dbHelper;
    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testLoginSuccess() {
        // Dodavanje korisnika kako bi testirali uspješnu prijavu
        dbHelper = new DBhelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        dbHelper.insertData("VasaEmailAdresa", "VaseKorisnickoIme", "VasaLozinka");

        // Unosimo postojeće korisničko ime i lozinku
        Espresso.onView(ViewMatchers.withId(R.id.username)).perform(ViewActions.clearText(),ViewActions.typeText("VaseKorisnickoIme"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.clearText(),ViewActions.typeText("VasaLozinka"), ViewActions.closeSoftKeyboard());

        // Klik na gumb za prijavu
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());

        dbHelper.deleteUser("VaseKorisnickoIme");
    }

    @Test
    public void testLoginFailure() {
        // Dodavanje korisnika kako bi testirali uspješnu prijavu
        dbHelper = new DBhelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        dbHelper.insertData("VasaEmailAdresa", "VaseKorisnickoIme", "VasaLozinka");

        // Unosimo pogrešno korisničko ime i/ili lozinku
        Espresso.onView(ViewMatchers.withId(R.id.username)).perform(ViewActions.clearText(),ViewActions.typeText("VaseKorisnickoIme"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.clearText(),ViewActions.typeText("KrivaLozinka"), ViewActions.closeSoftKeyboard());

        // Klik na Login button
        Espresso.onView(ViewMatchers.withId(R.id.loginButton))
                .perform(ViewActions.click());

        dbHelper.deleteUser("VaseKorisnickoIme");
    }

    @Test
    public void testLoginEmptyFields() {
        // 3. Slučaj: Podaci za prijavu ostaju prazni
        Espresso.onView(ViewMatchers.withId(R.id.username)).perform(ViewActions.clearText(),ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.clearText(),ViewActions.clearText());

        // Klik na Login button
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());
    }
    @Test
    public void testForgotPasswordButtonClick() {
        //4. Slučaj: forgot button
        // Inicijalizacija Intents prije korištenja
        Intents.init();

        // Kliknite na gumb "forgot"
        Espresso.onView(ViewMatchers.withId(R.id.forgotPassword)).perform(ViewActions.click());

        // Provjerite da li se otvara aktivnost PasswordActivity
        intended(hasComponent(PasswordActivity.class.getName()));

        // Oslobađanje Intents nakon upotrebe
        Intents.release();
    }
}
