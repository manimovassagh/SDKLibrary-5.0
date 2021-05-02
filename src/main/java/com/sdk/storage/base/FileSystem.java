package com.sdk.storage.base;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface FileSystem {
     final String[] WINEXEC = {"bat", "cmd", "exe", "msi"};
     final String[] LINEXEC = {"sh", "run"};
     final String[] MACEXEC = {"app", "dmg"};

     final String[] AUDIO = {"mp3", "wav", "ogg", "mpa", "aac"};
     final String[] IMAGE = {"jpg", "jpeg", "bmp", "png", "svg", "fig", "tif", "gif"};
     final String[] VIDEO = {"mp4", "avi", "mkv", "flv", "3gp", "nsv"};
     final String[] TEXT = {"txt", "xml", "fxml", "xmlns", "iml"};
     final String[] DOCUMENT = {"doc", "html", "html", " docx", "odt", "pdf"};

     final String[] LIBRARY = {"dll", "jar", "so", "pyd"};
     final String[] ARCHIVE = {"zip", "rar", "7zip", "tar.gz", "gz", "rar4"};

     final String JAVA_SOURCE = "java";
     final String CSHARP_SOURCE = "cs";
     final String C_SOURCE = "c";
     final String CPLUSPLUS_SOURCE = "cpp";
     final String PYTHON_SOURCE = "py";
     final String JAVASCRIPT_SOURCE = "js";
     final String PHP_SOURCE = "php";
     final String GOLANG_SOURCE = "go";
     final String RUBY_SOURCE = "rb";

     final String C_HEADER = "h";
     final String CPLUSPLUS_HEADER = "hh";

     String getPath();

     String getName();

     File getFile();

     long getSize() throws IOException;

     String getReadableSize() throws IOException;

     String getModifyDate() throws IOException;

     String getCreateDate() throws IOException;

     String getAccessDate() throws IOException;

     String getModifyTime() throws IOException;

     String getCreateTime() throws IOException;

     String getAccessTime() throws IOException;

     String getDirectoryName() throws IOException;

     String getDirectoryPath() throws IOException;

     String getOwner() throws IOException;

     String getType();

     String getBaseName();

     String getExtension();

     boolean isEmpty() throws IOException;
}
