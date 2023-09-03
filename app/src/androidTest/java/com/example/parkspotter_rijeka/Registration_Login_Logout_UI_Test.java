package com.example.parkspotter_rijeka;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class Registration_Login_Logout_UI_Test {
    private DBhelper dbHelper;
    private ActivityScenario<RegistrationActivity> scenario;

    @Rule
    public ActivityScenarioRule<RegistrationActivity> activityRule =
            new ActivityScenarioRule<>(RegistrationActivity.class);

    @Before
    public void setup() {
        // Initialize Espresso Intents capturing
        Intents.init();
        dbHelper = new DBhelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        // Start Activity
        scenario = ActivityScenario.launch(RegistrationActivity.class);
    }

    @Test
    public void testRegistration_Login_Logout() {
        // 1. Slučaj: Unose se podatci za registraciju, ali lozinke se ne podudaraju
        Espresso.onView(ViewMatchers.withId(R.id.usernameNovi)).perform(ViewActions.clearText(), ViewActions.typeText("noviKorisnik"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.emailNovi)).perform(ViewActions.clearText(), ViewActions.typeText("novi@primjer.com"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.passwordNovi)).perform(ViewActions.clearText(), ViewActions.typeText("lozinka123"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.repassword)).perform(ViewActions.clearText(), ViewActions.typeText("lozinka456"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.RegistrationBtn)).perform(ViewActions.click());

        // 2. Slučaj: Pokušajte registraciju bez unesenih podataka
        Espresso.onView(ViewMatchers.withId(R.id.RegistrationBtn)).perform(ViewActions.click());

        // 3. Slučaj: Unose se podatci za registraciju postojećeg korisnika
        // Dodavanje korisnika u bazu prije izvođenja testa
        dbHelper.insertData("postojeci@primjer.com", "postojeciKorisnik", "lozinka123");
        Espresso.onView(ViewMatchers.withId(R.id.usernameNovi)).perform(ViewActions.clearText(), ViewActions.typeText("postojeciKorisnik"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.emailNovi)).perform(ViewActions.clearText(), ViewActions.typeText("postojeci@primjer.com"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.passwordNovi)).perform(ViewActions.clearText(), ViewActions.typeText("lozinka123"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.repassword)).perform(ViewActions.clearText(), ViewActions.typeText("lozinka123"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.RegistrationBtn)).perform(ViewActions.click());

        // 4. Slučaj: Uspješna registracija novog korisnika
        onView(withId(R.id.usernameNovi)).perform(ViewActions.clearText(), typeText("noviKorisnik"), closeSoftKeyboard());
        onView(withId(R.id.emailNovi)).perform(ViewActions.clearText(), typeText("novi@primjer.com"), closeSoftKeyboard());
        onView(withId(R.id.passwordNovi)).perform(ViewActions.clearText(), typeText("lozinka123"), closeSoftKeyboard());
        onView(withId(R.id.repassword)).perform(ViewActions.clearText(), typeText("lozinka123"), closeSoftKeyboard());

        onView(withId(R.id.RegistrationBtn)).perform(click());

        // Provjera otvara li se LoginActivtiy
        intended(hasComponent(LoginActivity.class.getName()));

        // Oslobađanje Intents nakon upotrebe
        Intents.release();

        // 4.1. Slučaj: Pogrešno ime i/ili lozinka
        Espresso.onView(ViewMatchers.withId(R.id.username)).perform(ViewActions.clearText(), ViewActions.typeText("noviKorisnik"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.clearText(), ViewActions.typeText("KrivaLozinka"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());

        // 4.2. Slučaj: Podaci za prijavu ostaju prazni
        Espresso.onView(ViewMatchers.withId(R.id.username)).perform(ViewActions.clearText(), ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.clearText(), ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());


        // 4.3. Slučaj: Uspješna prijava s podacima novo registriranog korisnika
        onView(withId(R.id.username)).perform(ViewActions.clearText(), typeText("noviKorisnik"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(ViewActions.clearText(), typeText("lozinka123"), closeSoftKeyboard());
        Intents.init();
        onView(withId(R.id.loginButton)).perform(click());

        // Provjera otvara li se MainActivity
        intended(hasComponent(MainActivity.class.getName()));

        // 4.3.1. Logout iz MainActivity-a

        // Klik na stavku "Odjava"
        Espresso.onView(ViewMatchers.withId(R.id.my_drawer_layout)).perform(DrawerActions.open());

        Espresso.onView(ViewMatchers.withId(R.id.navigationDrawer)).perform(NavigationViewActions.navigateTo(R.id.odjava));


    }
    @After
    public void cleanup() {
        dbHelper.deleteUser("noviKorisnik");
        // Release Espresso Intents capturing
        Intents.release();
        // Close the activity scenario
        if (scenario != null) {
            scenario.close();
        }
    }
}
