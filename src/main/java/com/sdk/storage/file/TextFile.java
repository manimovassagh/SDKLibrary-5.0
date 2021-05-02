package com.sdk.storage.file;

import com.sdk.data.structures.StringList;
import com.sdk.data.types.Strings;
import com.sdk.storage.base.FileOperation;
import com.sdk.tools.ExternalTools;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TextFile extends FileOperation {

    public TextFile(String path) {
        super(path);
    }

    public String read() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path.toFile()));
        StringBuilder sb = new StringBuilder();

        String st;
        while ((st = br.readLine()) != null) {
            sb.append(st).append("\n");
        }

        return sb.toString();
    }

    public List<String> readAllLines() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path.toFile()));
        List<String> lines = new ArrayList<>();

        String st;
        while ((st = br.readLine()) != null) {
            lines.add(st);
        }

        return lines;
    }

    public String readFirstLine(boolean text) throws IOException {
        List<String> content = readAllLines();
        if (content.isEmpty()) {
            return new Strings().getEmptyString();
        }

        if (text) {
            for (String s : content) {
                if (!s.isEmpty() && !s.equals("\n")) {
                    return s;
                }
            }
        } else {
            return content.get(0);
        }

        return new Strings().getEmptyString();
    }

    public String readLastLine(boolean text) throws IOException {
        List<String> content = readAllLines();
        if (content.isEmpty()) {
            return new Strings().getEmptyString();
        }

        if (text) {
            StringList list = new StringList(true).add(content.toArray(new String[0]));
            list.reverse();

            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).isEmpty() && !list.get(i).equals("\n")) {
                    return list.get(i);
                }
            }
        } else {
            return content.get(content.size() - 1);
        }

        return new Strings().getEmptyString();
    }

    public boolean write(String text) {
        try {
            FileWriter wr = new FileWriter(getPath());
            wr.write(text);

            wr.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean write(String[] text) {
        if (new Strings().isNullOrEmpty(text)) {
            return false;
        }

        try {
            FileWriter wr = new FileWriter(getPath());
            for (String line : text) {
                wr.write(line);
                wr.write("\n");
            }

            wr.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean append(String text, boolean nextLine) {
        try {
            if (nextLine) {
                Files.write(path, text.concat("\n").getBytes(), StandardOpenOption.APPEND);
            } else {
                Files.write(path, text.getBytes(), StandardOpenOption.APPEND);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean append(String[] text) {
        try {
            for (String line : text) {
                Files.write(path, line.concat("\n").getBytes(), StandardOpenOption.APPEND);
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean search(String key, boolean match) {
        try {
            List<String> content = readAllLines();

            for (String s : content) {
                if (match) {
                    if (s.equals(key)) {
                        return true;
                    }
                } else {
                    if (s.contains(key)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean clear() {
        try {
            FileWriter wr = new FileWriter(getPath());
            wr.write(new Strings().getEmptyString());

            wr.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String key) throws IOException {
        List<String> content = readAllLines();
        content.remove(key);

        return write(content.toArray(new String[0]));
    }

    public int countLines() throws IOException {
        return Files.readAllLines(path).size();
    }

    public int countEmptyLines() throws IOException {
        List<String> content = readAllLines();
        int count = 0;

        for (String line : content) {
            if (new Strings().isNullOrEmpty(line) && !line.equals("\n")) {
                count++;
            }
        }

        return count;
    }

    public int countWords() throws IOException {
        return read().split("\\s+").length;
    }

    public int countCharacters(boolean empty) throws IOException {
        int total = read().toCharArray().length;

        if (empty) {
            return total;
        } else {
            char[] content = read().toCharArray();
            int count = 0;

            for (char c : content) {
                if (String.valueOf(c).equals(" ")) {
                    count++;
                }
            }

            return total - count;
        }
    }

    public int countMatches(String key, boolean match) throws IOException {
        return new Strings().countMatches(read(), key, "\\s+", match);
    }

}
