package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.exceptions.DaoException;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.PasswordDaoImpl.getPasswordDaoInstance;
import static java.lang.String.format;

public class PasswordServiceImpl implements PasswordService {

    private static final int SALT_LENGTH = 32;
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int ASCII_CHARACTER_COUNT = 128;
    private static final int ASCII_STARTING_CHARACTER = 33;

    private static final PasswordServiceImpl passwordServiceImpl = new PasswordServiceImpl();

    private PasswordServiceImpl() {
    }

    public static PasswordServiceImpl getPasswordServiceInstance() {
        return passwordServiceImpl;
    }

    @Override
    public boolean savePassword(Account account, String accountPassword) {
        String salt = generateSalt();
        Password passwordObject = new Password(createHashedPassword(accountPassword, salt), salt);
        return getPasswordDaoInstance().savePassword(account, passwordObject);
    }

    private String createHashedPassword(String password, String salt) {
        byte[] passwordBytes = password.getBytes();
        byte[] saltBytes = salt.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            messageDigest.update(passwordBytes);
            messageDigest.update(saltBytes);
            byte[] digest = messageDigest.digest();
            return bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new DaoException("dao: generate hash for password method failed: " + e.getMessage());
        }
    }

    private String generateSalt() {
        StringBuilder salt = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < SALT_LENGTH; i++) {
            int randomChar = ASCII_STARTING_CHARACTER + random.nextInt(ASCII_CHARACTER_COUNT);
            salt.append((char) randomChar);
        }
        return salt.toString();
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder passwordHash = new StringBuilder();
        for (byte byteValue : bytes) {
            passwordHash.append(format("%02X", byteValue));
        }
        return passwordHash.toString();
    }

    @Override
    public boolean checkPassword(String enteredPassword) {
        return false;
    }

    @Override
    public boolean changePassword(Password password) {
        return false;
    }

}
