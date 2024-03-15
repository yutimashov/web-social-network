package com.getjavajob.training.timashovy.socialnetwork.service.util;

import com.getjavajob.training.timashovy.socialnetwork.dao.util.exceptions.DaoException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import static java.lang.String.format;

public final class CredentialHashUtil {

    private static final int SALT_LENGTH = 32;
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int ASCII_CHARACTER_COUNT = 128;
    private static final int ASCII_STARTING_CHARACTER = 33;

    public static String hashCredential(String password, String salt) {
        byte[] passwordBytes = password.getBytes();
        byte[] saltBytes = salt.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            messageDigest.update(passwordBytes);
            messageDigest.update(saltBytes);
            return bytesToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new DaoException("dao: generate hash for password method failed: " + e.getMessage());
        }
    }
    public static String generateSalt() {
        StringBuilder salt = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < SALT_LENGTH; i++) {
            int randomChar = ASCII_STARTING_CHARACTER + random.nextInt(ASCII_CHARACTER_COUNT);
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
