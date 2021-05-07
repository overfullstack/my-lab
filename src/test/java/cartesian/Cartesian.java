package cartesian;

import org.junitpioneer.jupiter.CartesianProductTest;

class Cartesian {
    
    @CartesianProductTest
    @CartesianIntSource(numbers = { 1, 2, 4 })
    void testIntChars(int num1) {
        System.out.println(num1);
    }    
}
