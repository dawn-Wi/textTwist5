package com.example.texttwist5;

public class ShuffleHelper
{
    public static String shuffle(String word) {
        char[] array = word.toCharArray();
        int count = array.length;
        char temp, temp2;
        int randomNum1, randomNum2;

        for (int i = 0; i < count; i++) {
            randomNum1 = (int) (Math.random() * array.length);
            temp = array[randomNum1];
            randomNum2 = (int) ((Math.random() * array.length));
            temp2 = array[randomNum2];
            array[randomNum1] = temp2;
            array[randomNum2] = temp;
        }
        return new String(array);
    }

    public static void shuffle(char[] array)
    {
        int count = array.length;
        char temp, temp2;
        int randomNum1, randomNum2;

        for (int i = 0; i < count; i++) {
            randomNum1 = (int) (Math.random() * array.length);
            temp = array[randomNum1];
            randomNum2 = (int) ((Math.random() * array.length));
            temp2 = array[randomNum2];
            array[randomNum1] = temp2;
            array[randomNum2] = temp;
        }
    }
}
