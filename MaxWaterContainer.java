public class MaxWaterContainer{
    public static int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;

        while (left < right) {
            // Calculate the width and height of the container
            int width = right - left;
            int currentHeight = Math.min(height[left], height[right]);
            // Calculate the area and update maxArea if necessary
            int currentArea = width * currentHeight;
            maxArea = Math.max(maxArea, currentArea);

            // Move the pointer pointing to the shorter line
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }

    public static void main(String[] args) {
        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        int maxWater = maxArea(height);
        System.out.println("The maximum amount of water a container can store is: " + maxWater);
    }
}

