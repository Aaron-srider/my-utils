package com.ndsec.app.AppImageLoader.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IOUtils {

    public static void copyWithOutClosing(InputStream in, OutputStream out) {
        try {
            BufferedInputStream bufferedIn = new BufferedInputStream(in);
            BufferedOutputStream bufferedOut = new BufferedOutputStream(out);
            int len = 0;
            byte[] buffer = new byte[1024 * 1024];
            while ((len = bufferedIn.read(buffer)) != -1) {
                bufferedOut.write(buffer, 0, len);
            }
            bufferedOut.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyWithOutClosing(byte[] data, OutputStream out) {
        copyWithOutClosing(new ByteArrayInputStream(data), out);
    }

    public static void copyWithOutClosing(byte[] data, String dest) {
        try {
            copyWithOutClosing(new ByteArrayInputStream(data), Files.newOutputStream(Paths.get(dest)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyThenClose(InputStream in, OutputStream out) {
        try (
                BufferedInputStream bufferedIn = new BufferedInputStream(in);
                BufferedOutputStream bufferedOut = new BufferedOutputStream(out);) {
            int len = 0;
            byte[] buffer = new byte[1024 * 1024];
            while ((len = bufferedIn.read(buffer)) != -1) {
                bufferedOut.write(buffer, 0, len);
            }
            bufferedOut.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyThenClose(byte[] data, OutputStream out) {
        copyWithOutClosing(new ByteArrayInputStream(data), out);
    }

    public static void copyThenClose(byte[] data, String dest) {
        try {
            copyThenClose(new ByteArrayInputStream(data), Files.newOutputStream(Paths.get(dest)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] readAllBytes(InputStream in) {
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(
                        in);) {
            byte[] buffer = new byte[1024 * 1024];
            int len;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] readAllBytes(String src) {
        try {
            return readAllBytes(Files.newInputStream(Paths.get(src)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
