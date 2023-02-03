package fit.wenchao.utils.io;

import java.io.*;

public class IOUtils {

    public static void copyWithOutClosing(InputStream in, OutputStream out) {
        try {
            BufferedInputStream bufferedIn = new BufferedInputStream(in);
            BufferedOutputStream bufferedOut =
                    new BufferedOutputStream(out);
            int len = 0;
            byte[] buffer = new byte[1024 * 1024];
            while ((len = bufferedIn.read(buffer)) != -1) {
                bufferedOut.write(buffer, 0, len);
            }
            bufferedOut.flush();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyThenClose(InputStream in, OutputStream out) {
        try (
                BufferedInputStream bufferedIn = new BufferedInputStream(in);
                BufferedOutputStream bufferedOut =
                        new BufferedOutputStream(out);
        ) {
            int len = 0;
            byte[] buffer = new byte[1024 * 1024];
            while ((len = bufferedIn.read(buffer)) != -1) {
                bufferedOut.write(buffer, 0, len);
            }
            bufferedOut.flush();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] readAllBytes(InputStream in) {
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                BufferedInputStream bufferedInputStream =
                        new BufferedInputStream(
                                in);
        ) {
            byte[] buffer = new byte[1024 * 1024];
            int len;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return out.toByteArray();
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
