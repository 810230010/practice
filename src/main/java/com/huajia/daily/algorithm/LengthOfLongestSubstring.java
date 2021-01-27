package com.huajia.daily.algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LengthOfLongestSubstring {
    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("c"));
    }

    // v1
    public static int lengthOfLongestSubstring(String s) {
        if(s == null || s.length() == 0) return 0;

        if(s.trim().length() == 0) return 1;

        int maxLen = 0;
        int sLen = s.length();
        for(int i=0; i<sLen; i++) {
            for(int j=i+1; j<=sLen; j++) {
                String partStr = s.substring(i, j);
                boolean hasDuplicate = false;
                char[] charArray = partStr.toCharArray();
                Set<Character> set = new HashSet<>();
                for(char c : charArray) {
                    set.add(c);
                }

                if(set.size() != partStr.length()) {
                    continue;
                }

                if(set.size() > maxLen) {
                    maxLen = set.size();
                }
            }
        }

        return maxLen;
    }

    // 无重复的最长子串  // dvdf
    public static int lengthOfLongestSubstringV2(String s) {
        if (s.length()==0) return 0;
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        int max = 0;
        int left = 0;
        for(int i = 0; i < s.length(); i ++){
            if(map.containsKey(s.charAt(i))){
                left = Math.max(left,map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i),i);
            max = Math.max(max,i-left+1);
        }
        int x = 1;
        return max;
    }
}
