public class SecondLargest {
    public static int findSecondLargest(int[] arr) {
        if (arr.length < 2) {
            return -1;
        }

        int first = Integer.MIN_VALUE;
        int second = Integer.MIN_VALUE;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > first) {
                second = first;
                first = arr[i];
            } else if (arr[i] > second && arr[i] != first) {
                second = arr[i];
            }
        }
        return second;
    }

    public static void main(String[] args) {
        int[] arr = {10, 5, 20, 8, 12};
        System.out.println("Second largest element is: " + findSecondLargest(arr));
    }
}

