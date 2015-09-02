package com.worldwar.utility;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ListsTest {

    public static byte[] emptyArray = new byte[]{};
    public static byte[] firstArray = "Hello".getBytes();
    public static byte[] secondArray = "World".getBytes();
    public static byte[] thirdArray = ", ".getBytes();
    public static byte[] combinedTwo = "HelloWorld".getBytes();
    public static byte[] combinedThree = "Hello, World".getBytes();

    @Test
    public void couldConcatTwoArrays() {
        assertThat(Lists.concat(firstArray, secondArray), is(combinedTwo));
    }

    @Test
    public void couldConcatThreeArrays() {
        assertThat(Lists.concat(firstArray, thirdArray, secondArray), is(combinedThree));
    }

    @Test
    public void couldConcatNullArrays() {
        assertThat(Lists.concat(null, firstArray, null, thirdArray, null, secondArray), is(combinedThree));
    }

    @Test
    public void couldConcatEmptyArrays() {
        assertThat(Lists.concat(emptyArray, firstArray, null, thirdArray, null, secondArray), is(combinedThree));
    }

    @Test
    public void couldConcatOneEmptyArray() {
        assertThat(Lists.concat(emptyArray), is(emptyArray));
    }

    @Test
    public void couldConcatOneNullArray() {
        assertThat(Lists.concat(null), is(emptyArray));
    }
}