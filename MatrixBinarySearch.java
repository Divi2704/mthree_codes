public class MatrixBinarySearch {
    public static boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false; // Edge case for empty matrix
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int low = 0;
        int high = rows * cols - 1;

        // Perform binary search
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int midValue = matrix[mid / cols][mid % cols]; // Map 1D index back to 2D

            if (midValue == target) {
                return true; // Target found
            } else if (midValue < target) {
                low = mid + 1; // Search right half
            } else {
                high = mid - 1; // Search left half
            }
        }

        return false; // Target not found
    }

    public static void main(String[] args) {
        int[][] matrix = {
            {1, 3, 5, 7},
            {10, 11, 16, 20},
            {23, 30, 34, 60}
        };

        int target = 3;
        System.out.println("Is target present? " + searchMatrix(matrix, target)); // Output: true
    }
}
