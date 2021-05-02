package com.sdk.data.types;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import static com.sdk.tools.ExternalTools.toArrayList;
import java.util.ArrayList;
import java.util.Collections;

public class Strings {

    public boolean isNullOrEmpty(String input) {
        if (Objects.isNull(input)) {
            return true;
        }

        if (input.isEmpty()) {
            return true;
        }

        return false;
    }

    public boolean isNullOrEmpty(String[] input) {
        if (Objects.isNull(input)) {
            return true;
        }

        if (input.length == 0) {
            return true;
        }

        return false;
    }

    public String[] getEmptyArray() {
        return new String[]{};
    }

    public String getEmptyString() {
        return new String();
    }

    public String replace(String input, String oldW, String newW) {
        if (isNullOrEmpty(input)) {
            return getEmptyString();
        }

        return input.replaceAll(oldW, newW);
    }

    public int countMatches(String input, String key, String separator, boolean match) {
        if (isNullOrEmpty(input)) {
            return -1;
        }

        if (match) {
            String[] array = input.split(separator);
            int count = 0;

            for (String s : array) {
                if (s.equals(key)) {
                    count++;
                }
            }

            return count;
        }

        return StringUtils.countMatches(input, key);
    }

    public String reverse(String input) {
        if (isNullOrEmpty(input)) {
            return getEmptyString();
        }

        return new StringBuilder(input).reverse().toString();
    }

    public String[] reverse(String[] input) {
        List<String> list = new ArrayList<>(Arrays.asList(input));
        Collections.reverse(list);
        
        return list.toArray(new String[0]);
    }

    public void clearStringBuilder(StringBuilder sb) {
        sb.setLength(0);
    }

    public boolean isNumber(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isText(String input) {
        char[] array = input.toCharArray();

        for (char item : array) {
            if (Character.isDigit(item)) {
                return false;
            }
        }

        return true;
    }

    public String arrayToString(String[] array, String split) {
        if (Objects.isNull(array)) {
            return getEmptyString();
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);

            if (!Objects.isNull(split)) {
                sb.append(split);

                if (i + 1 < array.length) {
                    sb.append(split);
                }
            }
        }

        return sb.toString();
    }

    public StringBuilder toStringBuilder(String input) {
        return new StringBuilder(input);
    }

    public StringBuilder arrayToStringBuilder(String[] array, String split) {
        return toStringBuilder(arrayToString(array, split));
    }

    public String[] removeElement(String[] input, int index) {
        if (index > input.length) {
            index = input.length;
        }

        List<String> items = toArrayList(input);
        items.remove(index);

        return items.toArray(new String[0]);
    }

    public String[] removeElement(String[] input, String key) {
        List<String> items = toArrayList(input);
        items.remove(key);

        return items.toArray(new String[0]);
    }
}
