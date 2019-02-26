package pl.edu.pjatk.tau;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MathematicTest {

    Mathematic mathematic;

    @Before
    public void init() {
       mathematic = new Mathematic();
    }

    @Test
    public void sumNumbersCheck() {
        List<Integer> n = new ArrayList<Integer>();
        n.add(2);
        n.add(2);
        int result = mathematic.sumNumbers(n);
        assertEquals(4, result);
    }

    @Test
    public void sumCheck() {
        double result = mathematic.sum();
        assertEquals(1, result, 0.001);
    }

}
