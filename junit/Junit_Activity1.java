package activities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.testng.Assert.assertEquals;

public class Junit_Activity1 {

    static ArrayList<String> list;

    // Initialize test fixtures before each test method
    @BeforeEach
    void setUp() throws Exception {
        list = new ArrayList<String>();
        list.add("alpha"); // at index 0
        list.add("beta"); // at index 1
    }


    @Test
    @Order(1)
    public void insertTest()
    {
        // Assert size of list
        assertEquals(2, list.size(), "Wrong size");

        list.add("swaytha");
        assertEquals(3, list.size(), "Wrong size");
        // Assert individual elements

        assertEquals("alpha", list.get(0), "Wrong element");
        assertEquals("beta", list.get(1), "Wrong element");
        assertEquals("swaytha", list.get(2), "Wrong element");
    }

    @Test
    @Order(2)
    public void replaceTest()
    {
        // Assert size of list
        assertEquals(2, list.size(), "Wrong size");

        list.add("swaytha");
        assertEquals(3, list.size(), "Wrong size");

        // Replace element in ArrayList
        list.set(1, "abc");

        // Assert individual elements

        assertEquals("alpha", list.get(0), "Wrong element");
        assertEquals("abc", list.get(1), "Wrong element");
        assertEquals("swaytha", list.get(2), "Wrong element");
    }
}
