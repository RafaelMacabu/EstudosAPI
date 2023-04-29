package com.rest;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Encoded {

    public static void main(String[] args) {
        String usernamePassword = "myUsername:myPassword";

        String base64Encoded = Base64.getEncoder().encodeToString(usernamePassword.getBytes());
        System.out.println("Encoded: " + base64Encoded);
        byte[] decodedBytes = Base64.getDecoder().decode(base64Encoded);
        System.out.println("Decoded: " + new String(decodedBytes));
    }
}
