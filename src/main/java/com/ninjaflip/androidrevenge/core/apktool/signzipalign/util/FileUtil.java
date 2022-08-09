package com.ninjaflip.androidrevenge.core.apktool.signzipalign.util;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.MessageDigest;

public class FileUtil {

    public static String getFileExtension(File file) {
        if (file == null) {
            return "";
        }
        return file.getName().substring(file.getName().lastIndexOf(".") + 1);
    }

    public static String getFileNameWithoutExtension(File file) {
        String fileName = file.getName();
        int pos = fileName.lastIndexOf(".");
        if (pos > 0) {
            fileName = fileName.substring(0, pos);
        }
        return fileName;
    }

    public static String createChecksum(File file, String shaAlgo) {
        try {
            InputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            MessageDigest complete = MessageDigest.getInstance(shaAlgo);
            int numRead;

            do {
                numRead = fis.read(buffer);
                if (numRead > 0) {
                    complete.update(buffer, 0, numRead);
                }
            } while (numRead != -1);

            fis.close();
            return DatatypeConverter.printHexBinary(complete.digest()).toLowerCase();
        } catch (Exception e) {
            throw new IllegalStateException("could not create checksum for " + file + " and algo " + shaAlgo + ": " + e.getMessage(), e);
        }
    }

    public static File createTempDirectory(String prefix)
            throws IOException {
        final File temp;

        temp = Files.createTempDirectory(prefix + Long.toString(System.nanoTime())).toFile();

        return (temp);
    }
}
