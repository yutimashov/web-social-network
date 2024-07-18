package com.getjavajob.training.timashovy.socialnetwork.service.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import static java.lang.String.format;
import static java.security.MessageDigest.getInstance;

public final class PasswordUtil {

    private static final int SALT_LENGTH = 32;
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int ASCII_CHARACTER_AMOUNT = 94;
    private static final int ASCII_STARTING_CHARACTER = 32;

    public static String hashCredentialData(String password, String salt) {
        byte[] passwordBytes = password.getBytes();
        byte[] saltBytes = salt.getBytes();
        MessageDigest messageDigest = null;
        try {
            messageDigest = getInstance(HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        messageDigest.update(passwordBytes);
        messageDigest.update(saltBytes);
        return bytesToHex(messageDigest.digest());
    }

    public static String generateSalt() {
        StringBuilder salt = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < SALT_LENGTH; i++) {
            int randomChar = ASCII_STARTING_CHARACTER + random.nextInt(ASCII_CHARACTER_AMOUNT);
            salt.append((char) randomChar);
        }
        return salt.toString();
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder passwordHash = new StringBuilder();
        for (byte byteValue : bytes) {
            passwordHash.append(format("%02X", byteValue));
        }
        return passwordHash.toString();
    }

}
