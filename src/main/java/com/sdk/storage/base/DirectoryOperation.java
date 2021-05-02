package com.sdk.storage.base;

import com.sdk.data.types.Strings;
import com.sdk.tools.ExternalTools;
import com.sdk.tools.OperatingSystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

public class DirectoryOperation implements DirectorySystem {
    protected Path path;

    public DirectoryOperation(String path) {
        if (path.trim().equals(".")) {
            this.path = Paths.get(new OperatingSystem().getExecutePath());
        } else {
            this.path = Paths.get(path.trim());
        }
    }

    @Override
    public String getName() {
        return path.getFileName().toString();
    }

    @Override
    public String getPath() {
        return path.toAbsolutePath().toString();
    }

    @Override
    public File getFile() {
        return path.toFile();
    }

    @Override
    public String getDirectoryPath() throws IOException {
        return path.toRealPath().getParent().toString();
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
    public long getSize() {
        return FileUtils.sizeOfDirectory(path.toFile());
    }

    @Override
    public String getReadableSize() {
        return ExternalTools.toReadableSize(FileUtils.sizeOfDirectory(path.toFile()));
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
    public int countFiles() throws IOException {
        int count = 0;
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(getPath()));

        for (Path pth : directoryStream) {
            if (Files.exists(pth) && Files.isRegularFile(pth)) {
                count++;
            }

        }
        return count;
    }

    @Override
    public int countDirectories() throws IOException {
        int count = 0;
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(getPath()));

        for (Path pth : directoryStream) {
            if (Files.exists(pth) && Files.isDirectory(pth)) {
                count++;
            }

        }
        return count;
    }

    @Override
    public int countSubFiles() throws IOException {
        return (int) Files.walk(path).parallel().filter(p -> p.toFile().isFile()).count();
    }

    @Override
    public int countSubDirectories() throws IOException {
        return (int) Files.walk(path).parallel().filter(p -> p.toFile().isDirectory()).count() - 1;
    }

    @Override
    public String getDirectoryInfo() {
        return null;
    }

    @Override
    public String getOwner() throws IOException {
        return Files.getOwner(path).getName();
    }

    @Override
    public List<File> getSubFiles() throws IOException {
        return Files.walk(path).filter(Files::isRegularFile)
                .map(Path::toFile).collect(Collectors.toList());
    }

    @Override
    public List<File> getSubDirectories() throws IOException {
        return Files.walk(path).filter(Files::isDirectory)
                .map(Path::toFile).collect(Collectors.toList());
    }

    @Override
    public List<File> getFiles() throws IOException {
        List<File> files = new ArrayList<>();

        Files.list(path).forEach(file -> {
            if (Files.exists(file) && Files.isRegularFile(file)) {
                files.add(file.toFile());
            }
        });

        return files;
    }

    @Override
    public List<File> getDirectories() throws IOException {
        List<File> directories = new ArrayList<>();

        Files.list(path).forEach(file -> {
            if (Files.exists(file) && Files.isDirectory(file)) {
                directories.add(file.toFile());
            }
        });

        return directories;
    }

    @Override
    public long countFilesLines() {
        return 0;
    }

    public boolean exists() {
        return Files.exists(path) && Files.isDirectory(path);
    }

    public boolean copy(String destination) throws IOException {
        try {
            FileUtils.copyDirectory(path.toFile(), new File(destination));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean copy(String destination, boolean replace) {
        try {
            DirectoryOperation dp = new DirectoryOperation(destination);
            if (!replace && dp.exists() && this.getName().equals(dp.getName())) {
                return true;
            }

            FileUtils.copyDirectory(path.toFile(), new File(destination));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean move(String destination) {
        try {
            FileUtils.moveDirectory(path.toFile(), new File(destination));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean move(String destination, boolean replace) {
        try {
            DirectoryOperation dp = new DirectoryOperation(destination);
            if (!replace && dp.exists() && this.getName().equals(dp.getName())) {
                return true;
            }

            FileUtils.moveDirectory(path.toFile(), new File(destination));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rename(String name) {
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

    public boolean isEmpty() {
        return FileUtils.sizeOfDirectory(path.toFile()) == 0;
    }

    public boolean delete() {
        try {
            FileUtils.deleteDirectory(path.toFile());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean create() {
        try {
            Files.createDirectory(path);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eraseFiles(boolean subs) {
        try {
            List<File> files = null;

            if (subs) {
                files = getSubFiles();
            } else {
                files = getFiles();
            }

            for (File file : files) {
                Files.deleteIfExists(file.toPath());
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eraseDirectories() {
        try {
            for (File file : getDirectories()) {
                if (!file.getAbsolutePath().equals(path.toAbsolutePath().toString())) {
                    FileUtils.deleteDirectory(file);
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eraseAll(boolean subs) {
        try {
            return eraseDirectories() && eraseFiles(subs);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean search(File file) {
        try {
            if (Objects.isNull(file)) {
                return false;
            }

            List<File> files = getSubFiles();

            for (File f : files) {
                if (f.getName().equals(file.getName())) {
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean search(String name) {
        try {
            if (new Strings().isNullOrEmpty(name)) {
                return false;
            }

            List<File> files = getSubFiles();

            for (File f : files) {
                if (f.getName().equals(name)) {
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
