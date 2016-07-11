package pl.org.opi.management.utils;

import com.google.common.base.Charsets;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

public class HashUtils {
    private static final Random RAND = new Random();

    public static String getRandomHash() {
        return DigestUtils.md5Hex(new Date().toString() + RAND.nextInt());
    }

    public static String getHash(String str) {

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] encoded = md5.digest(str.getBytes(Charsets.UTF_8));
            return new String(Hex.encodeHex(encoded));

        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
