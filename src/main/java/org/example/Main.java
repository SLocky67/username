package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static AtomicInteger count3;
    static AtomicInteger count4;
    static AtomicInteger count5;


    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        count3 = new AtomicInteger(0);
        count4 = new AtomicInteger(0);
        count5 = new AtomicInteger(0);

        Thread thread1 = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    counter(text);
                }
            }
        });
        thread1.start();


        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                if (isSameLetter(text)) {
                    counter(text);
                }
            }
        });
        thread2.start();

        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                if (isIncreasingLetters(text)) {
                    counter(text);
                }
            }
        });
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("Красивых слов с длиной 3: " + count3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + count4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + count5.get() + " шт");
    }

    public static void counter(String text) {
        int lengthText = text.length();
        if (lengthText == 3) count3.getAndIncrement();
        if (lengthText == 4) count4.getAndIncrement();
        if (lengthText == 5) count5.getAndIncrement();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }


    public static boolean isPalindrome(String text) {
        int left = 0;
        int right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static boolean isSameLetter(String text) {
        char firstLetter = text.charAt(0);
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != firstLetter) {
                return false;
            }
        }
        return true;
    }

    public static boolean isIncreasingLetters(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

}
