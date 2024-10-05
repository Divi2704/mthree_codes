class Solution {
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        
        int start = 0, maxLength = 1;
        
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);        // Odd length palindrome
            int len2 = expandAroundCenter(s, i, i + 1);    // Even length palindrome
            int len = Math.max(len1, len2);
            
            if (len > maxLength) {
                start = i - (len - 1) / 2;
                maxLength = len;
            }
        }
        
        return s.substring(start, start + maxLength);
    }
    
    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    // Add a main method to test the longestPalindrome function
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test with a sample string
        String testString = "adadb";
        String result = solution.longestPalindrome(testString);
        
        // Print the result
        System.out.println("Longest Palindromic Substring: " + result);
    }
}
