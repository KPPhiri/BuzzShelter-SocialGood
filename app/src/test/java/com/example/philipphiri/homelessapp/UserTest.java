package com.example.philipphiri.homelessapp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by philipphiri on 4/16/18.
 */
public class UserTest {
    @Test
    public void setUserName() throws Exception {
        User a = new User("default", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A");

        //test 50 random letter combinations
        for (int i = 0; i < 50; i++) {
            String name = getRandomWord((5));
            a.setUserName(name);
            assertEquals(name, a.getUserName());

        }

        //test that string <1 does not change word
        a.setUserName("regWord");
        a.setUserName("");
        assertEquals("regWord", a.getUserName());

        //test that string >10 does not change word
        a.setUserName("regWord");
        a.setUserName("bigwordletter");
        assertEquals("regWord", a.getUserName());


    }


    private String getRandomWord(int length) {
        String r = "";
        for (int i = 0; i < length; i++) {
            r += (char) (Math.random() * 26 + 97);
        }
        return r;
    }
}