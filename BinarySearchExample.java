package com.example;

public class BinarySearchExample {

    // Binary search method
    public static int binarySearch(int[] array, int target) {
        int left = 0;                // Starting index
        int right = array.length - 1; // Ending index

        while (left <= right) {
            int mid = left + (right - left) / 2; // Middle index

            // Check if target is at mid
            if (array[mid] == target) {
                return mid; // Return index of target
            }

            // If target is greater, ignore left half
            if (array[mid] < target) {
                left = mid + 1;
            }
            // If target is smaller, ignore right half
            else {
                right = mid - 1;
            }
        }
        return -1; // If target is not present in array
    }

    public static void main(String[] args) {
        // Sample sorted array
        int[] numbers = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19,21};

        // Target value to find
        int target = 7;

        // Call binary search method
        int result = binarySearch(numbers, target);

        // Output the result
        if (result != -1) {
            System.out.println("Element found at index: " + result);
        } else {
            System.out.println("Element not found");
        }
    }
}
