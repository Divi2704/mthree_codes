import java.util.*;

public class DuplicateArray {
    public static void main(String[] args) {
        int arr[] = {1, 1, 2, 2, 2, 3, 3};
        rearrangeArray(arr);
        System.out.println("The array after rearranging is:");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    static void rearrangeArray(int[] arr) {
        // Step 1: Use a HashSet to collect unique elements
        Set<Integer> uniqueSet = new HashSet<>();
        List<Integer> duplicates = new ArrayList<>();

        for (int num : arr) {
            if (!uniqueSet.add(num)) {
                // If the element is already in the set, it's a duplicate
                duplicates.add(num);
            }
        }

        // Step 2: Place unique elements from the set at the start of the array
        int index = 0;
        for (int num : uniqueSet) {
            arr[index++] = num;
        }

        // Step 3: Add duplicates after the unique elements
        for (int num : duplicates) {
            arr[index++] = num;
        }
    }
}
