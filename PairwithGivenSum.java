import java.util.Arrays;

public class PairwithGivenSum {
    public static void main(String[] args) {
        int[] arr = {10, 15, 3, 7};
        int targetSum = 17; // Example target sum
        
        // Sort the array
        Arrays.sort(arr);
        
        System.out.println("Pair with given sum exists: " + hasPairWithSum(arr, targetSum));
    }

    public static boolean hasPairWithSum(int[] arr, int sum) {
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            int currentSum = arr[left] + arr[right];

            if (currentSum == sum) {
                return true; // Pair found
            } else if (currentSum < sum) {
                left++; // Move left pointer to the right to increase the sum
            } else {
                right--; // Move right pointer to the left to decrease the sum
            }
        }
        return false; // No pair found
    }
}

