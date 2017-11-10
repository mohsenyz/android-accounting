package com.mphj.accountry;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void helloToMohsen() throws Exception {
        assertTrue("mohsen" == "mohsen");
    }


    @Test
    public void helloToMohsen1() throws Exception {
        assertFalse("mohsen" == new String("mohsen"));
    }


    @Test
    public void helloToMohsen2() throws Exception {
        assertFalse("mohsen" == new StringBuilder().append("mohsen").toString());
    }
}