package com.sdk.storage.base;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface DirectorySystem {

    String getName();

    String getPath() throws IOException;

    File getFile();

    String getDirectoryPath() throws IOException;

    String getDirectoryName() throws IOException;

    long getSize();

    String getReadableSize();

    String getModifyDate() throws IOException;

    String getModifyTime() throws IOException;

    String getCreateDate() throws IOException;

    String getCreateTime() throws IOException;

    String getAccessDate() throws IOException;

    String getAccessTime() throws IOException;

    int countFiles() throws IOException;

    int countDirectories() throws IOException;

    int countSubFiles() throws IOException;

    int countSubDirectories() throws IOException;

    public String getDirectoryInfo();

    String getOwner() throws IOException;

    List<File> getSubFiles() throws IOException;

    List<File> getSubDirectories() throws IOException;

    List<File> getFiles() throws IOException;

    List<File> getDirectories() throws IOException;

    long countFilesLines();
}
