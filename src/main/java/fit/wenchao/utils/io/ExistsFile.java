package fit.wenchao.utils.io;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;

import static fit.wenchao.utils.string.StrUtils.ft;

public class ExistsFile {

    File file;

    public ExistsFile(File file) {
        this.file = file;
    }

    public ExistsFile(String pathname) {
        file = new File(pathname);
    }

    public ExistsFile(String parent, String child) {
        file = new File(parent, child);
    }

    public ExistsFile(File parent, String child) {
        file = new File(parent, child);
    }

    public ExistsFile(URI uri) {
        file = new File(uri);
    }

    private void checkConstructFile() throws FileNotFoundException {
        if (file != null && !file.exists()) {
            throw new FileNotFoundException(ft("can not new File obj, cause file:{} does not exists!",
                    file.getName()));
        }
    }

    private void checkUsingFile() throws FileNotFoundException {
        if (file != null && !file.exists()) {
            throw new FileNotFoundException(ft("Target \"{}\" does not exists!",
                    file.toString()));
        }
    }

    public String getName() throws FileNotFoundException {
        checkUsingFile();
        return file.getName();
    }

    public String getParent() throws FileNotFoundException {
        checkUsingFile();
        return file.getParent();
    }

    public File getParentFile() throws FileNotFoundException {
        checkUsingFile();
        return file.getParentFile();
    }

    public String getPath() throws FileNotFoundException {
        checkUsingFile();
        return file.getPath();
    }

    public boolean isAbsolute() throws FileNotFoundException {
        checkUsingFile();
        return file.isAbsolute();
    }

    public String getAbsolutePath() throws FileNotFoundException {
        checkUsingFile();
        return file.getAbsolutePath();
    }

    public File getAbsoluteFile() throws FileNotFoundException {
        checkUsingFile();
        return file.getAbsoluteFile();
    }

    public String getCanonicalPath() throws IOException {
        checkUsingFile();
        return file.getCanonicalPath();
    }

    public File getCanonicalFile() throws IOException {
        checkUsingFile();
        return file.getCanonicalFile();
    }

    public URL toURL() throws MalformedURLException, FileNotFoundException {
        checkUsingFile();
        return file.toURL();
    }

    public URI toURI() throws FileNotFoundException {
        checkUsingFile();
        return file.toURI();
    }

    public boolean canRead() throws FileNotFoundException {
        checkUsingFile();
        return file.canRead();
    }

    public boolean canWrite() throws FileNotFoundException {
        checkUsingFile();
        return file.canWrite();
    }

    public boolean exists() {
        return file.exists();
    }

    public boolean isDirectory() throws FileNotFoundException {
        checkUsingFile();
        return file.isDirectory();
    }

    public boolean isFile() throws FileNotFoundException {
        checkUsingFile();
        return file.isFile();
    }

    public boolean isHidden() throws FileNotFoundException {
        checkUsingFile();
        return file.isHidden();
    }

    public long lastModified() throws FileNotFoundException {
        checkUsingFile();
        return file.lastModified();
    }

    public long length() throws FileNotFoundException {
        checkUsingFile();
        return file.length();
    }

    private void checkCreateFile() throws FileAlreadyExistsException {
        if(file.exists()) {
            String fileType = "";
            if( file.isDirectory()) {
                fileType = "dir";
            } else {
                fileType = "file";
            }
            String failMsg = ft("There is already a {} \"{}\"",fileType,file);
            throw new FileAlreadyExistsException(failMsg);
        }
    }

    public boolean createNewFile() throws IOException {
        checkCreateFile();
        return file.createNewFile();
    }

    public boolean delete() throws FileNotFoundException {
        checkUsingFile();
        return file.delete();
    }

    public void deleteOnExit() throws FileNotFoundException {
        checkUsingFile();
        file.deleteOnExit();
    }

    public String[] list() throws FileNotFoundException {
        checkUsingFile();
        return file.list();
    }

    public String[] list(FilenameFilter filter) throws FileNotFoundException {
        checkUsingFile();
        return file.list(filter);
    }

    public File[] listFiles() throws FileNotFoundException {
        checkUsingFile();
        return file.listFiles();
    }

    public File[] listFiles(FilenameFilter filter) throws FileNotFoundException {
        checkUsingFile();
        return file.listFiles(filter);
    }

    public File[] listFiles(FileFilter filter) throws FileNotFoundException {
        checkUsingFile();
        return file.listFiles(filter);
    }


    private void checkMkdir() throws FileAlreadyExistsException {
        if(file.exists()) {
            String fileType = "";
            if( file.isDirectory()) {
                fileType = "dir";
            } else {
                fileType = "file";
            }
            String failMsg = ft("There is already a {} \"{}\"",fileType,file);
            throw new FileAlreadyExistsException(failMsg);
        }
    }

    public boolean mkdir() throws FileAlreadyExistsException {
        checkMkdir();
        return file.mkdir();
    }

    public boolean mkdirs() throws FileAlreadyExistsException {
        checkMkdir();
        return file.mkdirs();
    }




    /**
     * 移动本文件（不是目录）到指定文件或目录，如果目标与本文件在同一目录，相当于重命名
     * @param dest 目标文件
     * @throws IOException 1.源文件不存在或是个目录 2.目标文件已经存在 3.目标目录不存在
     */
    public void moveTo(File dest) throws IOException {
        try{
            FileUtils.moveFile(this.file, dest);
        } catch (FileExistsException ex) {
            FileUtils.moveFileToDirectory(this.file, dest, true);
        }
    }

    public boolean setLastModified(long time) throws FileNotFoundException {
        checkUsingFile();
        return file.setLastModified(time);
    }

    public boolean setReadOnly() throws FileNotFoundException {
        checkUsingFile();
        return file.setReadOnly();
    }

    public boolean setWritable(boolean writable, boolean ownerOnly) throws FileNotFoundException {
        checkUsingFile();
        return file.setWritable(writable, ownerOnly);
    }

    public boolean setWritable(boolean writable) throws FileNotFoundException {
        checkUsingFile();
        return file.setWritable(writable);
    }

    public boolean setReadable(boolean readable, boolean ownerOnly) throws FileNotFoundException {
        checkUsingFile();
        return file.setReadable(readable, ownerOnly);
    }

    public boolean setReadable(boolean readable) throws FileNotFoundException {
        checkUsingFile();
        return file.setReadable(readable);
    }

    public boolean setExecutable(boolean executable, boolean ownerOnly) throws FileNotFoundException {
        checkUsingFile();
        return file.setExecutable(executable, ownerOnly);
    }

    public boolean setExecutable(boolean executable) throws FileNotFoundException {
        checkUsingFile();
        return file.setExecutable(executable);
    }

    public boolean canExecute() throws FileNotFoundException {
        checkUsingFile();
        return file.canExecute();
    }

    public long getTotalSpace() throws FileNotFoundException {
        return file.getTotalSpace();
    }

    public long getFreeSpace() throws FileNotFoundException {
        return file.getFreeSpace();
    }

    public long getUsableSpace() {
        return file.getUsableSpace();
    }

    public int compareTo(File pathname) {
        return file.compareTo(pathname);
    }

    @Override
    public boolean equals(Object obj) {
        return file.equals(obj);
    }

    @Override
    public int hashCode() {
        return file.hashCode();
    }

    @Override
    public String toString() {
        return file.toString();
    }

    public Path toPath() throws FileNotFoundException {
        checkUsingFile();
        return file.toPath();
    }

    public File getUnderlyingFile()  {
        return this.file;
    }

    /**
     * 拷贝一份自身到指定文件，如果目标已经存在且不是目录，将覆盖。
     * @param destFilePath 目标文件完整路径
     * @throws IOException 1.源文件是目录 2.源文件和目标文件相同 3.目标文件目录无法创建（已经存在且是个文件） 4.目标文件
     * 已经存在且没有写权限 5.目标文件已存在且是目录 6.内容没拷贝完全
     */
    public ExistsFile copyTo(String destFilePath) throws IOException {
        File dest = new File(destFilePath);
        if(dest.exists()) {
            throw new FileExistsException("Destination '" + dest + "' exists");
        }
        FileUtils.copyFile(this.file, dest);
        return new ExistsFile(dest);
    }

}
