package com.example.parkspotter_rijeka;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.is;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegistrationActivityInstrumentedTest {

    private DBhelper dbHelper;

    @Rule
    public ActivityTestRule<RegistrationActivity> activityRule =
            new ActivityTestRule<>(RegistrationActivity.class);

    @Before
    public void setUp() {
        // Inicijalizirajte DBHelper ovdje
        dbHelper = new DBhelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
    }

    @After
    public void tearDown() {
        // Obrišite korisnika nakon svakog testa
        dbHelper.deleteUser("noviKorisnik");
    }

    @Test
    public void testRegistrationSuccess() {
        // 1. Slučaj: Unose se podatci za registraciju
        Espresso.onView(ViewMatchers.withId(R.id.usernameNovi)).perform(ViewActions.clearText(),ViewActions.typeText("noviKorisnik"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.emailNovi)).perform(ViewActions.clearText(),ViewActions.typeText("novi@primjer.com"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.passwordNovi)).perform(ViewActions.clearText(),ViewActions.typeText("lozinka123"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.repassword)).perform(ViewActions.clearText(),ViewActions.typeText("lozinka123"), ViewActions.closeSoftKeyboard());

        // Klik na registration button
        Espresso.onView(ViewMatchers.withId(R.id.RegistrationBtn)).perform(ViewActions.click());
    }

    @Test
    public void testPasswordMismatch() {
        // 2. Slučaj: Unose se podatci za registraciju, ali lozinke se ne podudaraju
        Espresso.onView(ViewMatchers.withId(R.id.usernameNovi)).perform(ViewActions.clearText(),ViewActions.typeText("noviKorisnik"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.emailNovi)).perform(ViewActions.clearText(),ViewActions.typeText("novi@primjer.com"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.passwordNovi)).perform(ViewActions.clearText(),ViewActions.typeText("lozinka123"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.repassword)).perform(ViewActions.clearText(),ViewActions.typeText("lozinka456"), ViewActions.closeSoftKeyboard());

        // Klik na registration button
        Espresso.onView(ViewMatchers.withId(R.id.RegistrationBtn)).perform(ViewActions.click());
    }

    @Test
    public void testEmptyFields() {
        // 3. Slučaj: Pokušajte registraciju bez unesenih podataka
        Espresso.onView(ViewMatchers.withId(R.id.RegistrationBtn)).perform(ViewActions.click());
    }

    @Test
    public void testExistingUser() {
        // 4. Slučaj: Unose se podatci za registraciju postojećeg korisnika
        // Dodavanje korisnika u bazu prije izvođenja testa
        dbHelper.insertData("postojeci@primjer.com", "postojeciKorisnik", "lozinka123");

        // Unose se podatci za registraciju postojećeg korisnika
        Espresso.onView(ViewMatchers.withId(R.id.usernameNovi)).perform(ViewActions.clearText(),ViewActions.typeText("postojeciKorisnik"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.emailNovi)).perform(ViewActions.clearText(),ViewActions.typeText("postojeci@primjer.com"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.passwordNovi)).perform(ViewActions.clearText(),ViewActions.typeText("lozinka123"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.repassword)).perform(ViewActions.clearText(),ViewActions.typeText("lozinka123"), ViewActions.closeSoftKeyboard());

        // Klik na registration button
        Espresso.onView(ViewMatchers.withId(R.id.RegistrationBtn)).perform(ViewActions.click());

        // Brisanje korisnika nakon izvođenja testa
        dbHelper.deleteUser("postojeciKorisnik");
    }
}