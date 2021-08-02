package com.r2d2.doctorapp;

import android.icu.text.DateFormat;

import org.junit.Test;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class UserUnitTest {

    User u1 = new Doctor("Jason", "Hou", 2000, 9, 6, 123456789,
            "Male", "Good Doctor", "University of Toronto", 123466,
            "Psychology");
    @Test
    public void testEquals1() {
        assertEquals(u1.equals(null), false);
    }
    @Test
    public void testEquals2() {
        int i = 0;
        assertEquals(u1.equals(i), false);
    }
    @Test
    public void testEquals3() {
        User u3 = new Doctor("Jason", "Hou", 2000, 9, 6, 123456789,
                "Male", "Good Doctor", "University of Toronto", 123466,
                "Psychology");
        assertEquals(u1.equals(u3), true);
    }

    @Test
    public void testHashCode() {
        assertEquals(u1.hashCode(), 123456789);
    }

    @Test
    public void testToString() {

        assertEquals(u1.toString(), "Dr. Jason, Hou");
    }

    @Test
    public void testGetFirstName() {
        assertEquals(u1.getFirstName(), "Jason");
    }

    @Test
    public void testSetFirstName() {
        u1.setFirstName("Angus");
        assertEquals(u1.getFirstName(), "Angus");
    }

    @Test
    public void testGetLastName() {
        assertEquals(u1.getLastName(), "Hou");
    }

    @Test
    public void testSetLastName() {
        u1.setLastName("Lee");
        assertEquals(u1.getLastName(), "Lee");
    }

    @Test
    public void testGetBirthday() {
        Calendar c1 = new GregorianCalendar();
        c1.set(2000, 9, 6);
        Calendar c2 = u1.getBirthday();
        assertEquals(c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
                c1.get(Calendar.DATE) == c2.get(Calendar.DATE), true);
    }

    @Test
    public void testSetBirthday() {
        Calendar c = new GregorianCalendar();
        c.set(2000, 10, 31);
        u1.setBirthday(c);
        assertEquals(u1.getBirthday().equals(c), true);
    }

    @Test
    public void testGetSin() {
        assertEquals(u1.getSin(), 123456789);
    }

    @Test
    public void testSetSin() {
        u1.setSin(987654321);
        assertEquals(u1.getSin(), 987654321);
    }

    @Test
    public void testGetGender() {
        assertEquals(u1.getGender(), "Male");
    }

    @Test
    public void testSetGender() {
        u1.setGender("Female");
        assertEquals(u1.getGender(), "Female");
    }
}