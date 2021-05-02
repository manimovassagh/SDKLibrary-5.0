package com.sdk.network;

import com.sdk.console.Console;
import com.sdk.data.types.Strings;
import com.sdk.storage.base.FileOperation;
import com.sdk.storage.file.SingleFile;
import com.sdk.tools.ExternalTools;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class NetworkTools {

    private String url;

    public NetworkTools(String url) {
        this.url = url;
    }

    public boolean internetStatus() {
        try {
            URLConnection conn = new URL("http://www.google.com").openConnection();
            conn.connect();

            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean detectNetworkInterfaces() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface interf = interfaces.nextElement();
                if (interf.isUp() && !interf.isLoopback()) {
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkWebsite() {
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();

            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public long getFileSize() throws IOException {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
            urlConnection.setRequestMethod("HEAD");

            String lengthHeaderField = urlConnection.getHeaderField("content-length");
            Long result = lengthHeaderField == null ? null : Long.parseLong(lengthHeaderField);
            
            return result == null || result < 0L ? -1L : result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String getReadableSize() throws IOException {
        return ExternalTools.toReadableSize(getFileSize());
    }

    public String getFileName() throws MalformedURLException {
        return FilenameUtils.getName((new URL(url)).getPath());
    }

    public String getFileBaseName() throws MalformedURLException {
        return FilenameUtils.getBaseName((new URL(url)).getPath());
    }

    public String getFileExtension() throws MalformedURLException {
        return FilenameUtils.getExtension((new URL(url)).getPath());
    }

    public boolean downloadFile(boolean progress) {
        try {
            if (progress) {
                Thread th = new Thread(
                        new DownloadProgress(getFileSize(), new FileOperation(getFileName()))
                );

                th.start();
            }

            FileUtils.copyURLToFile(new URL(url), new File(getFileName()));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getServerIP() {
        try {
            InetAddress address = InetAddress.getByName(new URL(url).getHost());
            return address.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void ping(int timeout, int repeat, String start, boolean number) {
        try {

            for (int i = 0; i < repeat; i++) {
                HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(timeout);
                
                con.connect();

                if (!new Strings().isNullOrEmpty(start)) {
                    System.out.print(start);
                }

                if (number) {
                    System.out.print("[" + (i + 1) + "]  ");
                }

                int code = con.getResponseCode();
                if (code == 200) {
                    System.out.println("Replay from \"" + getServerIP() + "\" ok, return code (" + code + ")");
                } else {
                    System.out.println("No response");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
