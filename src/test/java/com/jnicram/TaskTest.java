package com.jnicram;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskTest {

    @Test
    public void shouldGeneratesProperEquation() {
        // given
        Pattern pattern = Pattern.compile("^\\d+([-+\\/*]\\d+)*");
        Task task = new Task();

        // when
        task.generateRandomEquation();

        // then
        Matcher matcher = pattern.matcher(task.getEquation());
        Assert.assertTrue(matcher.matches());
    }

    @Test
    public void shouldReturnsCorrectResultForEasyEquation() {
        // given
        Task task = new Task();
        task.setEquation("2+7");

        // when
        final double result = task.execute();

        // then
        Assert.assertEquals(9, result, 0);
    }

    @Test
    public void shouldReturnsCorrectResultForComplexEquation() {
        // given
        Task task = new Task();
        task.setEquation("2+7-4*43/34+4/2342-23*43-2");

        // when
        final double result = task.execute();

        // then
        Assert.assertEquals(-987.0571, result, 0.0001);
    }
}