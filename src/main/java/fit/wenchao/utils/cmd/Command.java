package fit.wenchao.utils.cmd;

import java.io.*;

public class Command {
    public static void main(String[] args) throws IOException, InterruptedException {
        Command command = new Command();
        ProcessResult result = command.sh("apt update", true, "wc123456");
        result = command.sh("ls -l", false, "");
        System.out.println(result.getRt());
        System.out.println(result.success());
        try (
                BufferedReader inputStreamReader =
                        new BufferedReader(new InputStreamReader(result.getResultStream()));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ) {
            String line = null;
            try {
                while ((line = inputStreamReader.readLine()) != null) {
                    byteArrayOutputStream.write(line.getBytes());
                    byteArrayOutputStream.write('\n');
                }
            }
            catch (IOException ex) {

            }

            System.out.println(byteArrayOutputStream.toString());
        }

    }

    public static String getProcConsoleResult(Process process) throws IOException {
        InputStream inputStream = process.getInputStream();
        InputStream errInput = process.getErrorStream();
        System.out.println(inputStream.available());
        System.out.println(errInput.available());
        try (
                BufferedReader inputStreamReader =
                        new BufferedReader(new InputStreamReader(inputStream));
                BufferedReader errorReader =
                        new BufferedReader(new InputStreamReader(errInput));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ) {
            String line = null;
            try {
                while ((line = inputStreamReader.readLine()) != null) {
                    byteArrayOutputStream.write(line.getBytes());
                    byteArrayOutputStream.write('\n');
                }
            }
            catch
            (IOException ex) {

            }
            try {
                while ((line = errorReader.readLine()) != null) {
                    byteArrayOutputStream.write(line.getBytes());
                    byteArrayOutputStream.write('\n');
                }
            }
            catch
            (IOException ex) {

            }
            return byteArrayOutputStream.toString();
        }


    }

    public ProcessResult sh(String command, boolean sudo, String password) throws IOException,
            InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        if (sudo) {
            if (password == null) {
                throw new IllegalArgumentException("You need to provide " +
                        "password for sudo");
            }
            command = "echo \"" + password + "\" | sudo -S " + command;
        }
        Process process = runtime.exec(new String[]{"sh", "-c", command});
        int i = process.waitFor();
        ProcessResult processResult = ProcessResult.newInstance();
        processResult.setNormalInfoInStream(process.getInputStream());
        processResult.setErrInStream(process.getErrorStream());
        processResult.setRt(i);
        return processResult;
    }

    static class ProcessResult {
        InputStream normalInfoInStream;
        InputStream errInStream;
        int rt;

        public static ProcessResult newInstance() {
            return new ProcessResult();
        }

        public void setNormalInfoInStream(InputStream inputStream) {
            this.normalInfoInStream = inputStream;
        }

        public InputStream getErrInStream() {
            return errInStream;
        }

        public void setErrInStream(InputStream inputStream) {
            this.errInStream = inputStream;
        }

        public InputStream getNormalInfoInputStream() {
            return normalInfoInStream;
        }

        public InputStream getResultStream() {
            return new SequenceInputStream(normalInfoInStream, errInStream);
        }

        public int getRt() {
            return rt;
        }

        public void setRt(int i) {
            this.rt = i;
        }

        public boolean success() {
            return rt == 0;
        }
    }

}
