package com.sdk.storage.base;

import com.sdk.data.structures.StringList;
import com.sdk.data.types.Strings;
import com.sdk.tools.ExternalTools;
import com.sdk.tools.OperatingSystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;

public class FileOperation implements FileSystem{
    protected Path path;

    public FileOperation(String path) {
        if (path.trim().equals(".")) {
            this.path = Paths.get(new OperatingSystem().getExecutePath());
        } else {
            this.path = Paths.get(path.trim());
        }
    }

    @Override
    public String getPath() {
        return path.toAbsolutePath().toString();
    }

    @Override
    public String getName() {
        return path.getFileName().toString();
    }

    @Override
    public File getFile() {
        return path.toFile();
    }

    @Override
    public long getSize() throws IOException {
        return Files.size(path);
    }

    @Override
    public String getReadableSize() throws IOException {
        return ExternalTools.toReadableSize(Files.size(path));
    }

    @Override
    public String getModifyDate() throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
        FileTime time = attrs.lastModifiedTime();

        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time.toMillis()));
    }

    @Override
    public String getCreateDate() throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
        FileTime time = attrs.creationTime();

        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time.toMillis()));
    }

    @Override
    public String getAccessDate() throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
        FileTime time = attrs.lastAccessTime();

        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time.toMillis()));
    }

    @Override
    public String getModifyTime() throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
        FileTime time = attrs.lastModifiedTime();

        return new SimpleDateFormat("HH:mm:ss").format(new Date(time.toMillis()));
    }

    @Override
    public String getCreateTime() throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
        FileTime time = attrs.creationTime();

        return new SimpleDateFormat("HH:mm:ss").format(new Date(time.toMillis()));
    }

    @Override
    public String getAccessTime() throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
        FileTime time = attrs.lastAccessTime();

        return new SimpleDateFormat("HH:mm:ss").format(new Date(time.toMillis()));
    }

    @Override
    public String getDirectoryName() throws IOException {
        try {
            return path.toRealPath().getParent().getFileName().toString();
        } catch (Exception e) {
            return path.toRealPath().getParent().toString();
        }
    }

    @Override
    public String getDirectoryPath() throws IOException {
        return path.toRealPath().getParent().toString();
    }

    @Override
    public String getOwner() throws IOException {
        return Files.getOwner(path).getName();
    }

    @Override
    public String getType() {
        String[][] mimeType = {WINEXEC, LINEXEC, MACEXEC, AUDIO, IMAGE, VIDEO, TEXT, DOCUMENT, LIBRARY, ARCHIVE};
        Strings strings = new Strings();

        if (exists()) {
            String ext = getExtension().toLowerCase();

            for (int i = 0; i < mimeType.length; i++) {
                StringList list = new StringList(true).add(mimeType[i]);
                if (list.contains(ext)) {
                    switch (i) {
                        case 0:
                            return "Windows executable file";

                        case 1:
                            return "Linux executable file(script)";

                        case 2:
                            return "Mac application";

                        case 3:
                            return "Audio file";

                        case 4:
                            return "Image file";

                        case 5:
                            return "Video file";

                        case 6:
                            return "Plain text file";

                        case 7:
                            return "Document file";

                        case 8:
                            return "Library file";

                        case 9:
                            return "Archive file";
                    }
                }

                list.clear();
            }

            if (ext.equals(JAVA_SOURCE)) {
                return "Java source file";
            } else if (ext.equals(CSHARP_SOURCE)) {
                return "cSharp source file";
            } else if (ext.equals(C_SOURCE)) {
                return "C source file";
            } else if (ext.equals(CPLUSPLUS_SOURCE)) {
                return "C++ source file";
            } else if (ext.equals(PYTHON_SOURCE)) {
                return "Python source file";
            } else if (ext.equals(JAVASCRIPT_SOURCE)) {
                return "Javascript source file";
            } else if (ext.equals(PHP_SOURCE)) {
                return "Php source file";
            } else if (ext.equals(GOLANG_SOURCE)) {
                return "GoLang Source file";
            } else if (ext.equals(RUBY_SOURCE)) {
                return "Ruby source file";
            } else if (ext.equals(C_HEADER)) {
                return "C header file";
            } else if (ext.equals(CPLUSPLUS_HEADER)) {
                return "C++ header file";
            }
            return "Unknown type";
        }
        return "No file";
    }

    @Override
    public String getBaseName() {
        return FilenameUtils.getBaseName(path.getFileName().toString());
    }

    @Override
    public String getExtension() {
        return FilenameUtils.getExtension(path.getFileName().toString());
    }

    @Override
    public boolean isEmpty() throws IOException {
        return Files.size(path) == 0;
    }

    public boolean exists() {
        return Files.exists(path) && Files.isRegularFile(path);
    }

    public boolean delete() throws IOException {
        return Files.deleteIfExists(path);
    }

    public boolean create() throws IOException {
        try {
            Files.createFile(path);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean copy(String destination) throws IOException {
        try {
            Files.copy(path, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean copy(String destination, boolean replace) {
        try {
            FileOperation fp = new FileOperation(destination);
            if (!replace && fp.exists() && this.getName().equals(fp.getName())) {
                return true;
            }

            Files.copy(path, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean move(String destination) {
        try {
            Files.move(path, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean move(String destination, boolean replace) {
        try {
            FileOperation fp = new FileOperation(destination);
            if (!replace && fp.exists() && this.getName().equals(fp.getName())) {
                return true;
            }

            Files.move(path, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rename(String name) throws IOException {
        try {
            String directoryPath = path.toRealPath().getParent().toString();
            Files.move(path, path.resolveSibling(name));

            if (new OperatingSystem().isWindows()) {
                path = Paths.get(directoryPath.concat("\\").concat(name));
            } else {
                path = Paths.get(directoryPath.concat("/").concat(name));
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getFileInfo() {
        try {
            StringBuilder sb = new StringBuilder();

            sb.append("[Name]             ").append(getName()).append("\n");
            sb.append("[Modify date]      ").append(getModifyDate()).append("\n");
            sb.append("[Path]             ").append(getPath()).append("\n");
            sb.append("[Modify time]      ").append(getModifyTime()).append("\n");
            sb.append("[Size]             ").append(getReadableSize()).append("\n");
            sb.append("[Create date]      ").append(getCreateDate()).append("\n");
            sb.append("[Directory name]   ").append(getDirectoryName()).append("\n");
            sb.append("[Create time]      ").append(getCreateTime()).append("\n");
            sb.append("[Directory path]   ").append(getDirectoryPath()).append("\n");
            sb.append("[access time]      ").append(getAccessTime()).append("\n");
            sb.append("[Base name]        ").append(getBaseName()).append("\n");
            sb.append("[access date]      ").append(getAccessDate()).append("\n");
            sb.append("[Extension]        ").append(getExtension()).append("\n");
            sb.append("[File type]        ").append(getType());


            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean removeExtension() {
        try {
            String name = new Strings().reverse(path.getFileName().toString());
            int dotIndex = name.indexOf(".");
            if (dotIndex == -1) {
                return true;
            }

            return rename(new Strings().reverse(name.substring(dotIndex + 1)));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addExtension(String extension) {
        try {
            if (getExtension().equals(extension)) {
                return true;
            }

            if (extension.equals(".")) {
                return false;
            }

            if (!extension.startsWith(".")) {
                extension = ".".concat(extension);
            }

            String name = path.getFileName().toString().concat(extension);
            return rename(name);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eraseFile() {
        try {
            return delete() && create();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
