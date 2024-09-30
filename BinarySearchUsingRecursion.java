package com.example;

public class BinarySearchUsingRecursion {

    // Recursive binary search method
    public static int binarySearch(int[] array, int left, int right, int target) {
        // Base case: if the range is invalid
        if (left > right) {
            return -1; // Target not found
        }

        // Calculate mid-point
        int mid = left + (right - left) / 2;

        // If target is found at mid
        if (array[mid] == target) {
            return mid;
        }

        // If target is smaller than mid, search the left half
        if (array[mid] > target) {
            return binarySearch(array, left, mid - 1, target);
        }

        // If target is larger than mid, search the right half
        return binarySearch(array, mid + 1, right, target);
    }

    public static void main(String[] args) {
        // Sample sorted array
        int[] numbers = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19};

        // Target value to find
        int target = 17;

        // Call recursive binary search method
        int result = binarySearch(numbers, 0, numbers.length - 1, target);

        // Output the result
        if (result != -1) {
            System.out.println("Element found at index: " + result);
        } else {
            System.out.println("Element not found");
        }
    }
}
