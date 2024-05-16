package com.inpe.ServiceMachine.drivers.generators;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GeneratorHash {
    // Static method to generate SHA-256 hash for inputString
    public static String generate(String inputString) {
        String hash = null; // Initialize hash variable

        try {
            // Create a MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Update the digest with the input string bytes
            digest.update(inputString.getBytes());

            // Generate the hash value as a byte array
            byte[] hashBytes = digest.digest();

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0'); // Ensure two digits for each byte
                hexString.append(hex);
            }

            // Set the hash variable to the hexadecimal string representation of the hash value
            hash = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle NoSuchAlgorithmException (thrown if SHA-256 algorithm is not available)
            e.printStackTrace();
        }

        // Return the generated hash
        return hash;
    }
}
