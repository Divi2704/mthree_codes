import java.util.ArrayList;
import java.util.List;

public class LeadersinArray {
    public static void main(String[] args) {
        int[] arr = {16, 17, 4, 3, 5, 2};
        findLeaders(arr);
    }

    public static void findLeaders(int[] arr) {
        int n = arr.length;
        List<Integer> leaders = new ArrayList<>();

        // Start with the last element as a leader
        int maxFromRight = arr[n - 1];
        leaders.add(maxFromRight); // The last element is always a leader

        // Traverse the array from second last to the first
        for (int i = n - 2; i >= 0; i--) {
            if (arr[i] > maxFromRight) {
                leaders.add(arr[i]); // Add current element to leaders
                maxFromRight = arr[i]; // Update maxFromRight
            }
        }

        // Since we traversed from right to left, reverse the list to maintain original order
        System.out.println("Leaders in the array are:");
        for (int i = leaders.size() - 1; i >= 0; i--) {
            System.out.print(leaders.get(i) + " ");
        }
    }
}
