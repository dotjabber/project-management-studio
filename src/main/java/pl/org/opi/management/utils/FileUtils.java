package pl.org.opi.management.utils;

import java.io.InputStream;

public class FileUtils {
    public static InputStream getResourceAsInputStream(String path) {
        return FileUtils.class.getClassLoader().getResourceAsStream(path);
    }
}
