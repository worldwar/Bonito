package com.worldwar.utility;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class NumbersTest {

    @Test
    public void eightShouldBe4TimesOf2() {
        assertThat(Numbers.times(8, 2), is(4));
    }

    @Test
    public void nineShouldBe4TimesOf2() {
        assertThat(Numbers.times(9, 2), is(5));
    }
}