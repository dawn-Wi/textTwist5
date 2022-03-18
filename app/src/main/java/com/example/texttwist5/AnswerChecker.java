package com.example.texttwist5;

import java.util.HashMap;
import java.util.Map;

public class  AnswerChecker {
    public static boolean isAnswer(String base, String toTest) {
        Map<Character, Integer> baseMap = countLetters(base);
        Map<Character, Integer> toTestMap = countLetters(toTest);
        for (Character c : toTestMap.keySet()) {
            if (baseMap.containsKey(c)) {
                if (baseMap.get(c) < toTestMap.get(c)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private static Map<Character, Integer> countLetters(String word) {
        Map<Character, Integer> toReturn = new HashMap<>();
        char[] charWords = word.toCharArray();
        for (int i = 0; i < charWords.length; i++) {
            if (toReturn.containsKey(charWords[i])) {
                int currValue = toReturn.get(charWords[i]);
                toReturn.put(charWords[i], currValue + 1);
            } else {
                toReturn.put(charWords[i], 1);
            }
        }
        return toReturn;
    }
}
