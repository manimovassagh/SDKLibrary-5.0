package com.sdk.data.types;

import com.sdk.tools.ExternalTools;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;

public class Characters {

    public char[] getEmptyArray() {return new char[]{};}


    public char toLowerCase(char input) {
        return String.valueOf(input).toLowerCase().toCharArray()[0];
    }

    public char toUpperCase(char input) {
        return String.valueOf(input).toUpperCase().toCharArray()[0];
    }

    public char[] reverse(char[] input) {
        Character[] characters = ExternalTools.toCharacterArray(input);

        Collections.reverse(Arrays.asList(characters));
        return ArrayUtils.toPrimitive(characters);
    }

    public String arrayToString(char [] array, String split) {
        if (Objects.isNull(array) || Objects.isNull(split)) {
            return new Strings().getEmptyString();
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);

            if (i + 1 < array.length) {
                sb.append(split);
            }
        }

        return sb.toString();
    }
}
