package leetcode.num3.java;

public class Main {

    static void lengthOfLongestSubstring(String s) {
        char[] charArray = s.toCharArray();
        int[] locArray = new int[charArray.length];
        for(int i = 0; i < locArray.length; i++) {
            locArray[i] = i + 1;
        }
        for(int i = 0; i < charArray.length; i++) {
            for(int j = i - 1; j >= 0; j--) {
                if(charArray[i] == charArray[j]) {
                    locArray[i] = i - j;
                    break;
                }
            }
        }

    }
}
