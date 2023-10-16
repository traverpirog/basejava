package com.javaops.webapp;

public class MainString {
    public static void main(String[] args) {
        String[] strArray = new String[]{"1", "2", "3", "4", "5"};
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : strArray) {
            stringBuilder.append(str).append(", ");
        }
        System.out.println(stringBuilder); // result: 12345

        String str1 = "abc";
        String str2 = "ab" + "c";
        System.out.println(str1 == str2); // result: true

        String str3 = "abc";
        String str4 = "c";
        String str5 = "ab" + str4;
        System.out.println(str3 == str5); // result: false

        String str6 = "abc";
        String str7 = "c";
        String str8 = ("ab" + str7).intern();
        System.out.println(str6 == str8); // result: true
    }
}
