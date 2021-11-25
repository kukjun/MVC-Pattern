package lesson03.servlets;

public class Test {
    public static void main(String[] args) {
        long longValue = Long.MAX_VALUE;
        double doubleValue = longValue;

        for (int i = 0; i < 100; i++) {
            System.out.println("longType: " + longValue +
                    ", doubleType: " + doubleValue +
                    ", doubleType->long: " + (long) doubleValue);
            longValue -= 10;
            doubleValue = longValue;
        }

    }
}
