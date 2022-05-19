package com.example.benefit_app;

import static org.junit.Assert.assertEquals;

import com.example.benefit_app.Objects.User;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class waterUnitTest {
    User obj = new User();


    @Test

    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}