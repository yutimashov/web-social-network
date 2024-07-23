package com.getjavajob.training.timashovy.socialnetwork.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class InputStreamUtils {

    private InputStreamUtils() {
        throw new AssertionError();
    }

    public static boolean isEqual(InputStream avatar, InputStream otherAvatar) {
        try {
            return Arrays.equals(toByteArray(avatar), toByteArray(otherAvatar));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static byte[] toByteArray(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        return outputStream.toByteArray();
    }

}
