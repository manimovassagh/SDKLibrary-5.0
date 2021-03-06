package com.sdk.tools;

import com.sdk.data.types.Strings;
import javax.tools.ToolProvider;

import org.apache.commons.lang3.ArrayUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExternalTools {

    /**
     * Compile a java source code.
     *
     * @param path File path.
     * @return The result of compile.
     */
    public static boolean compileSourceCode(String path) {
        try {
            return ToolProvider.getSystemJavaCompiler().
                    run(null, null, null, path) == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get stack trace of an exception.
     *
     * @param e Exception.
     * @return Stack trace of exception.
     */
    public static String getPrintStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        return sw.toString();
    }

    /**
     * Converts int array to Integer array
     *
     * @param array The array want to convert.
     * @return The converted array.
     */
    public static Integer[] toIntegerArray(int[] array) {
        return ArrayUtils.toObject(array);
    }

    /**
     * Converts float array to Float array
     *
     * @param array The array want to convert.
     * @return The converted array.
     */
    public static Float[] toFloatArray(float[] array) {
        return ArrayUtils.toObject(array);
    }

    /**
     * Converts double array to Double array
     *
     * @param array The array want to convert.
     * @return The converted array.
     */
    public static Double[] toDoubleArray(double[] array) {
        return ArrayUtils.toObject(array);
    }

    /**
     * Converts long array to Long array
     *
     * @param array The array want to convert.
     * @return The converted array.
     */
    public static Long[] toLongArray(long[] array) {
        return ArrayUtils.toObject(array);
    }

    /**
     * Converts short array to Short array
     *
     * @param array The array want to convert.
     * @return The converted array.
     */
    public static Short[] toShortArray(short[] array) {
        return ArrayUtils.toObject(array);
    }

    /**
     * Converts byte array to Byte array
     *
     * @param array The array want to convert.
     * @return The converted array.
     */
    public static Byte[] toByteArray(byte[] array) {
        return ArrayUtils.toObject(array);
    }

    /**
     * Converts boolean array to Boolean array
     *
     * @param array The array want to convert.
     * @return The converted array.
     */
    public static Boolean[] toBooleanArray(boolean[] array) {
        return ArrayUtils.toObject(array);
    }

    /**
     * Converts char array to Character array
     *
     * @param array The array want to convert.
     * @return The converted array.
     */
    public static Character[] toCharacterArray(char[] array) {
        return ArrayUtils.toObject(array);
    }

    /**
     * Converts Object array to String array
     *
     * @param array The array want to convert.
     * @return The converted array.
     */
    public static String[] toStringArray(Object[] array) {
        return ArrayUtils.toStringArray(array);
    }

    /**
     * Converts String array to Numbers array
     *
     * @param numbers The array want to convert.
     * @return The converted array.
     */
    public static String[] toStringArray(Number[] numbers) {
        String[] tmp = new String[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            tmp[i] = String.valueOf(numbers[i]);
        }

        return tmp;
    }

    /**
     * Get a String array and convert it to List of Strings.
     *
     * @param input The array want to convert.
     * @return The List of Strings.
     */
    public static ArrayList<String> toArrayList(String[] input) {
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < input.length; i++) {
            list.add(input[i]);
        }

        return list;
    }

    /**
     * Gets time and convert to time with 0.
     *
     * @param number Time input.
     * @return The time with 0.
     */
    public static String toClockTime(int number) {
        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    /**
     * Converts seconds to time.
     *
     * @param seconds Input second.
     * @return Real time in (hh:mm:ss) format.
     */
    public static String secondsToTime(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        String h = toClockTime(hours), m = toClockTime(minutes), s = toClockTime(seconds);
        StringBuilder sb = new StringBuilder();

        if (!h.equals("00")) {
            sb.append(h).append(" : ");
        }

        if (!m.equals("00")) {
            sb.append(m).append(" : ");
        }

        if (!s.equals("00")) {
            sb.append(s);
        } else {
            return "0";
        }

        return sb.toString();
    }

    /**
     * Converts raw size to Human readable size.
     *
     * @param size Raw size.
     * @return Human readable size.
     */
    public static String toReadableSize(double size) {
        int i = 0;
        double tmp = size;

        if (size <= 0) {
            return String.valueOf(size);
        } else if (size < 1024) {
            return size + " B";
        }

        while (tmp > 1024) {
            tmp /= 1024;
            i++;
        }

        int dotPos = String.valueOf(tmp).indexOf(".");
        String real = String.valueOf(tmp).substring(0, dotPos);

        if ((dotPos + 3) > String.valueOf(tmp).length()) {
            real = real.concat(String.valueOf(tmp).substring(dotPos));
        } else {
            real = real.concat(String.valueOf(tmp).substring(dotPos, dotPos + 3));
        }

        switch (i) {
            case 1:
                return real + " KB";
            case 2:
                return real + " MB";
            case 3:
                return real + " GB";
            case 4:
                return real + " TB";

            default:
                return null;
        }
    }

    /**
     * Optimize input parameters, the parameter must be in "parameter" if it
     * contains space. The order is not important in this method.
     *
     * @param params Input parameters.
     * @return A set of optimized parameters.
     */
    public static Set<String> optimizeParameters(String[] params) {
        Set<String> parameters = new HashSet();
        StringBuilder sb = new StringBuilder();
        int begin = 0;
        int end = 0;

        try {
            if (params == null) {
                return null;
            } else {
                for (int i = 0; i < params.length; ++i) {
                    if (!params[i].startsWith("\"")) {
                        if (!params[i].equals("NULL")) {
                            parameters.add(params[i]);
                        }
                    } else {
                        params[i] = params[i].substring(1);

                        int n;
                        for (n = i; n < params.length; ++n) {
                            if (params[n].endsWith("\"")) {
                                end = n;
                                params[n] = params[n].substring(
                                        0, params[n].length() - 1);
                                break;
                            }
                        }

                        for (n = i; n <= end; ++n) {
                            if (n == end) {
                                sb.append(params[n]);
                            } else {
                                sb.append(params[n]).append(" ");
                            }

                            params[n] = "NULL";
                        }

                        parameters.add(sb.toString());
                        new Strings().clearStringBuilder(sb);
                    }
                }

                return parameters;
            }
        } catch (Exception var8) {
            var8.printStackTrace();
            return null;
        }
    }

    /**
     * Optimize input parameters, the parameter must be in "parameter" if it
     * contains space. It optimizes parameters by order from input.
     *
     * @param params Input parameters.
     * @return A List of optimized parameters.
     */
    public static List<String> optimizeParametersAsList(String[] params) {
        List<String> parameters = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int begin = 0;
        int end = 0;

        try {
            if (params == null) {
                return null;
            } else {
                for (int i = 0; i < params.length; ++i) {
                    if (!params[i].startsWith("\"")) {
                        if (!params[i].equals("NULL")) {
                            parameters.add(params[i]);
                        }
                    } else {
                        params[i] = params[i].substring(1);

                        int n;
                        for (n = i; n < params.length; ++n) {
                            if (params[n].endsWith("\"")) {
                                end = n;
                                params[n] = params[n].substring(
                                        0, params[n].length() - 1);
                                break;
                            }
                        }

                        for (n = i; n <= end; ++n) {
                            if (n == end) {
                                sb.append(params[n]);
                            } else {
                                sb.append(params[n]).append(" ");
                            }

                            params[n] = "NULL";
                        }

                        parameters.add(sb.toString());
                        new Strings().clearStringBuilder(sb);
                    }
                }

                return parameters;
            }
        } catch (Exception var8) {
            var8.printStackTrace();
            return null;
        }
    }

    /**
     * Optimize a single parameter, the parameter must be in "parameter" if it
     * contains space.
     *
     * @param input Input parameter.
     * @return A optimized parameter.
     */
    public static String optimizeSingleParameter(String input) {
        String parameter = null;

        if (input == null) {
            return null;
        } else {
            int begin = input.indexOf("\"");
            int end;

            if (input.endsWith("\"")) {
                end = input.lastIndexOf("\"");
            } else {
                end = -1;
            }

            if (end == -1) {
                return null;
            } else {
                parameter = input.substring(begin + 1, end);
                return parameter;
            }
        }
    }
}
