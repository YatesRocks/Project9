import org.yates.io.InputHandler;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Predicate;

public class Main {
    private static final Random rnd = new Random();

    private static void display(int[] nums) {
        System.out.print("|");
        for (int e : nums)
            System.out.printf(" %d |", e);
        System.out.println();
    }

    private static int[] getArray() {
        int[] nums = new int[100];
        for (int i = 0; i < 100; i++)
            nums[i] = rnd.nextInt(100) + 1;
        return nums;
    }

    private static void one() {
        int[] nums = getArray();
        display(nums);
        int sum = Arrays.stream(nums).sum();
        System.out.println("The sum of all numbers is: " + sum);
        int avg = sum / nums.length;
        System.out.println("The average of all numbers is: " + avg);
    }

    private static void two() {
        Predicate<String> checkNumber = s -> {
            try {
                int value = Integer.parseInt(s);
                return value >= 0 && value <= 100;
            } catch (NumberFormatException e) {
                return false;
            }
        }; // TODO: Need to rewrite InputHandler to be generic. This is a temporary stopgap, don't have time atm
        InputHandler handler = InputHandler
                .builder()
                .prompt("Enter a value 1-100: ")
                .errorMessage("Bad input! ") // TODO: better error message
                .validator(checkNumber)
                .build();
        int choice = Integer.parseInt(handler.query()); // trust me this is safe
        int[] nums = getArray();
        int matches = 0;
        int firstMatchingIndex = 0;
        for (int i = 0; i < 100; i++) {
            if (nums[i] == choice) {
                if (matches == 0)
                    firstMatchingIndex = i;
                matches++;
            }
        }
        if (matches == 0) {
            System.out.println("No matches found :(");
            return;
        }
        System.out.println("Found " + matches + " occurrence(s) of " + choice + " in the array");
        System.out.println("The first matching index was: " + firstMatchingIndex);
        int min = Arrays.stream(nums).min().getAsInt();
        int max = Arrays.stream(nums).max().getAsInt();
        System.out.println("The min is: " + min + "; and the max is: " + max);
        // Rather nasty bit of work here, but I'm in a rush and it works
        System.out.println("The average is: " + getAverage(Arrays.stream(nums).mapToDouble(i -> (double) i).toArray()));
    }

    private static double getAverage(double[] array) {
        double sum = Arrays.stream(array).sum();
        return sum / array.length;
    }

    public static void main(String[] args) {
        System.out.println("Test getAverage");
        double[] arr = {2.0, 1.5, 1.5};
        System.out.println("Expect 1.66, got: " + getAverage(arr));
    }
}
 