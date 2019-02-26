package pl.edu.pjatk.tau;

import java.util.List;

public class Mathematic {

    public int sumNumbers(List<Integer> v) {
        int sum = 0;
        for (Integer x : v) sum += x;
        return sum;
    }

    public double sum() {
        double sum = 0.0;
        for (int i = 0; i < 10; i++) {
            sum += 0.1;
        }
        return sum;
    }

}
