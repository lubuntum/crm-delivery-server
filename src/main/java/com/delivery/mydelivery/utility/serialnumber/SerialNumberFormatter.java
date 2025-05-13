package com.delivery.mydelivery.utility.serialnumber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SerialNumberFormatter {
    private static final int STEP = 10000;
    private static final int MAX_CYCLE = 259_999;


    public static long normalize(long id) {
        // Use modulus to wrap around the ID
        return id % (MAX_CYCLE + 1);
    }

    public static String calcSerialNumber(long id) {
        // Normalize the ID
        id = normalize(id);

        // Calculate the character part
        char serialChar = (char) ('A' + (id / STEP));

        // Ensure the character is within valid range
        if (serialChar > 'Z') {
            serialChar = 'Z'; // Cap to 'Z' if it exceeds
        }

        // Calculate the numeric part
        long numberPart = id % STEP; // Get the remainder for the number part
        return formatSerialNumber(serialChar + String.valueOf(numberPart));
    }

    public static String formatSerialNumber(String input) {
        Pattern pattern = Pattern.compile("([a-zA-Z]+)(\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            String letterPart = matcher.group(1);
            String numberPart = matcher.group(2);

            // Format the number part to be 4 digits
            String formattedNumber = String.format("%04d", Integer.parseInt(numberPart));
            return letterPart + formattedNumber;
        }
        return input; // Return unchanged if format doesn't match
    }
}
