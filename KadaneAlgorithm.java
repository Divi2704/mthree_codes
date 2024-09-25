public class KadaneAlgorithm {
    
    public static int maxSubArraySum(int[] arr) {
        int maxEnding = arr[0];  
        int maxResult = arr[0];    
        
        for (int i = 1; i < arr.length; i++) {
            maxEnding = Math.max(maxEnding + arr[i], arr[i]);
            if (maxEnding > maxResult) {
                maxResult = maxEnding;
            }
        }
        return maxResult;
    }

    public static void main(String[] args) {
        int[] nums = {-2, 3, -8, 7, -1, 2, 3};
        int maxSum = maxSubArraySum(nums);
        System.out.println("The maximum sum of the contiguous subarray is: " + maxSum);
    }
}
