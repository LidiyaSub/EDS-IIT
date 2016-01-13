package com.softserveinc.edu.ita;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class EndUserUtils {

    public static void printMessage(String message) {
        String eventMessage = "[" + new java.util.Date().toLocaleString() + "] " + message;
        System.out.println("IIT EU Test" + eventMessage);
    }

    public static String readMessage() {
        try {
            return (new BufferedReader(new InputStreamReader(System.in))).readLine();
        } catch (Exception e) {
            return "";
        }
    }
}
