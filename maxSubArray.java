public class maxSubArray {
    
    public static void main(String[] args) {
        int[] array = {-1, 1, 6, 4, -1, -2, 3, 5, -4};
        Result result = maxSumSubarray(array);
        System.out.println("Maximum sum of contiguous subarray: " + result.maxSum);
        System.out.print("Subarray with maximum sum: ");
        for (int num : result.subarray) {
            System.out.print(num + " ");
        }
    }

    public static Result maxSumSubarray(int[] arr) {
        int maxSum = Integer.MIN_VALUE; 
        int startIndex = 0;
        int endIndex = 0;

        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int currentSum = 0;
                for (int k = i; k <= j; k++) {
                    currentSum += arr[k];
                }
                if (currentSum > maxSum) {
                    maxSum = currentSum;
                    startIndex = i;
                    endIndex = j;
                }
            }
        }

    
        int[] maxSubarray = new int[endIndex - startIndex + 1];
        System.arraycopy(arr, startIndex, maxSubarray, 0, maxSubarray.length);

        return new Result(maxSum, maxSubarray);
    }

    // Class to hold the result
    static class Result {
        int maxSum;
        int[] subarray;

        Result(int maxSum, int[] subarray) {
            this.maxSum = maxSum;
            this.subarray = subarray;
        }
    }
}

