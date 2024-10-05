public class MatrixSearch {
    public static boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false; // Edge case for empty matrix
        }

        int rows = matrix.length;
        int cols = matrix[0].length;

        // Start from the top-right corner
        int row = 0;
        int col = cols - 1;

        while (row < rows && col >= 0) {
            if (matrix[row][col] == target) {
                return true; // Target found
            } else if (matrix[row][col] < target) {
                row++; // Move down if the target is greater
            } else {
                col--; // Move left if the target is smaller
            }
        }

        return false; // Target not found
    }

    public static void main(String[] args) {
        int[][] matrix = {
            {1, 4, 7, 11},
            {2, 5, 8, 12},
            {3, 6, 9, 16},
            {10, 13, 14, 17}
        };

        int target = 5;
        System.out.println("Is target present? " + searchMatrix(matrix, target)); // Output: true
    }
}
