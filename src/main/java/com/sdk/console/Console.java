package com.sdk.console;

import com.sdk.data.structures.StringList;
import com.sdk.data.types.Strings;
import com.sdk.tools.ExternalTools;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class Console {

    private int time;
    private int line;
    private boolean animation;

    public Console(int time, int line, boolean animation) {
        this.time = time;
        this.line = line;
        this.animation = animation;
    }

    public Console() {
        this.time = 1;
        this.line = 20;
        this.animation = false;
    }

    private void printLine() {
        if (this.animation) {
            this.print("\n", false);
        } else {
            System.out.println();
        }

    }

    private void printLine(String text, boolean nextLine) {
        if (this.animation) {
            this.print(text, nextLine);
        } else if (nextLine) {
            System.out.println(text);
        } else {
            System.out.print(text);
        }

    }

    public void sleep(int time) {
        try {
            Thread.sleep((long) time);
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }

    }

    public void sleep() {
        sleep(time);
    }

    public void printCharacters(char c, int length, boolean nextLine) {
        for (int i = 0; i < length; ++i) {
            System.out.print(c);
        }

        if (nextLine) {
            this.printLine();
        }

    }

    public void clear() {
        for (int i = 0; i < this.line; ++i) {
            this.printLine();
        }

    }

    public void clear(int line) {
        for (int i = 0; i < line; ++i) {
            this.printLine();
        }

    }

    public void pressEnter(boolean before, boolean after) {
        if (before) {
            this.printLine();
        }

        this.printLine("Press enter to continue!...", true);
        (new Scanner(System.in)).nextLine();
        if (after) {
            this.printLine();
        }

    }

    public int ask(String message, boolean repeat) {
        return this.ask(message, "Y", "N", repeat);
    }

    public int ask(String message, String first, String second, boolean repeat) {
        String answer = null;

        do {
            this.printLine(message.concat("? [" + first + "/" + second + "]: "), false);
            answer = this.getInput(1, true);
            if (answer.equals((new String(first)).toLowerCase())) {
                return 1;
            }

            if (answer.equals((new String(second)).toLowerCase())) {
                return 0;
            }
        } while (repeat);

        return -1;
    }

    public int ask(String message, String[] options, boolean repeat) {
        if (Objects.isNull(options)) {
            return -1;
        } else {
            do {
                String msg = message + " [";

                for (int i = 0; i < options.length; ++i) {
                    msg = msg + options[i];
                    if (i + 1 < options.length) {
                        msg = msg + "/";
                    }
                }

                msg = msg + "]: ";
                this.printLine(msg, false);
                String answer = this.getInput(1, true);

                StringList list = new StringList(true).add(options);
                list.toLowerCase();

                if (list.contains(answer)) {
                    return list.indexOf(answer);
                }
            } while (repeat);

            return -1;
        }
    }

    public void print(String input, boolean nextLine, int time) {
        if (!input.isEmpty()) {
            char[] array = input.toCharArray();
            char[] var5 = array;
            int var6 = array.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                char c = var5[var7];
                if (this.animation) {
                    this.sleep(time);
                }

                System.out.print(c);
            }

            if (nextLine) {
                this.printLine();
            }

        }
    }

    public void printLines(String[] input, boolean nextLine, int time) {
        if (!Objects.isNull(input)) {
            String[] var4 = input;
            int var5 = input.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                String s = var4[var6];
                if (this.animation) {
                    this.sleep(time);
                }

                System.out.print(s);
            }

            if (nextLine) {
                this.printLine();
            }

        }
    }

    public void printLines(String[] input, boolean nextLine) {
        this.printLines(input, nextLine, this.time);
    }

    public void print(String input, boolean nextLine) {
        this.print(input, nextLine, this.time);
    }

    public String getInput(int mode, boolean trim) {
        String input = (new Scanner(System.in)).nextLine();
        switch (mode) {
            case 1:
                input = input.toLowerCase();
                break;
            case 2:
                input = input.toUpperCase();
        }

        if (trim) {
            input = input.trim();
        }

        return input;
    }

    public String getInput(int mode, boolean trim, int length) {
        int max = 0;
        String input = this.getInput(mode, trim);
        StringBuilder buildString = new StringBuilder();
        char[] in = input.toCharArray();
        if (in.length == 0) {
            return (new Strings()).getEmptyString();
        } else {
            if (length > input.length()) {
                max = input.length();
            } else {
                max = length;
            }

            for (int i = 0; i < max; ++i) {
                buildString.append(in[i]);
            }

            return buildString.toString();
        }
    }

    public void leftAngledTriangle(int lines, boolean number) {
        int tmp = 0;

        for (int i = 0; i <= lines; ++i) {
            for (int j = 0; j < i; ++j) {
                if (number) {
                    if (tmp > 9) {
                        tmp = 0;
                    }

                    this.printLine(String.valueOf(tmp++), false);
                } else {
                    this.printLine("*", false);
                }
            }

            this.printLine();
        }

    }

    public void isoscelesTriangle(int lines, boolean number) {
        int tmp = 0;

        for (int rowNumber = 1; rowNumber <= lines; ++rowNumber) {
            for (int j = 1; j <= lines - rowNumber; ++j) {
                this.printLine(" ", false);
            }

            for (int k = 1; k <= 2 * rowNumber - 1; ++k) {
                if (number) {
                    if (tmp > 9) {
                        tmp = 0;
                    }

                    this.printLine(String.valueOf(tmp++), false);
                } else {
                    this.printLine("*", false);
                }
            }

            this.printLine();
        }

    }

    public void rightAngledTriangle(int lines, boolean number) {
        int tmp = 0;

        for (int i = 1; i <= lines; ++i) {
            int k;
            for (k = lines; k > i; --k) {
                this.printLine(" ", false);
            }

            for (k = 1; k <= i; ++k) {
                if (number) {
                    if (tmp > 9) {
                        tmp = 0;
                    }

                    this.printLine(String.valueOf(tmp++), false);
                } else {
                    this.printLine("*", false);
                }
            }

            this.printLine();
        }

    }

    public void reverseLeftAngledTriangle(int lines, boolean number) {
        int tmp = 0;

        for (int i = 1; i <= lines; ++i) {
            for (int j = lines - i; j >= 0; --j) {
                if (number) {
                    if (tmp > 9) {
                        tmp = 0;
                    }

                    this.printLine(String.valueOf(tmp++), false);
                } else {
                    this.printLine("*", false);
                }
            }

            this.printLine();
        }

    }

    public void diamond(int lines, boolean number) {
        int tmp = 0;

        int i;
        int j;
        for (i = 0; i < lines; ++i) {
            for (j = 0; j < lines - i - 1; ++j) {
                this.printLine(" ", false);
            }

            for (j = 0; j < 2 * i + 1; ++j) {
                if (number) {
                    if (tmp > 9) {
                        tmp = 0;
                    }

                    this.printLine(String.valueOf(tmp++), false);
                } else {
                    this.printLine("*", false);
                }
            }

            this.printLine();
        }

        for (i = 0; i < lines - 1; ++i) {
            for (j = 0; j <= i; ++j) {
                this.printLine(" ", false);
            }

            for (j = 2 * lines - 2 * i - 3; j > 0; --j) {
                if (number) {
                    if (tmp > 9) {
                        tmp = 0;
                    }

                    this.printLine(String.valueOf(tmp++), false);
                } else {
                    this.printLine("*", false);
                }
            }

            this.printLine();
        }

    }

    public void squareEmpty(int lines) {
        int m = lines;

        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= m; ++j) {
                if (i != 1 && i != m) {
                    if (j != 1 && j != m) {
                        this.printLine("   ", false);
                    } else {
                        this.printLine(" * ", false);
                    }
                } else {
                    this.printLine(" * ", false);
                }
            }

            this.printLine();
        }

    }

    public void squareFill(int lines) {
        for (int i = 1; i <= lines; ++i) {
            for (int j = 1; j <= lines; ++j) {
                this.printLine(" * ", false);
            }

            this.printLine();
        }

    }

    public void printError(String text, boolean nextLine) {
        this.printLine(ConsoleForeground.RED_BOLD + text + ConsoleForeground.RESET, nextLine);
    }

    public void printSuccess(String text, boolean nextLine) {
        this.printLine(ConsoleForeground.GREEN_BOLD + text + ConsoleForeground.RESET, nextLine);
    }

    public void printInformation(String text, boolean nextLine) {
        this.printLine(ConsoleForeground.BLUE_BOLD + text + ConsoleForeground.RESET, nextLine);
    }

    public int printQuestion(String text, boolean repeat) {
        return this.ask(ConsoleForeground.CYAN_BOLD + text, repeat);
    }

    public void printWarning(String text, boolean nextLine) {
        this.printLine(ConsoleForeground.YELLOW_BOLD + text + ConsoleForeground.RESET, nextLine);
    }

    public void printExceptionStackTrace(Exception e, boolean nextLine) {
        this.printError(ExternalTools.getPrintStackTrace(e), nextLine);
    }

    public void printExceptionMessage(Exception e, boolean nextLine) {
        this.printError(e.getMessage(), nextLine);
    }

    public void printList(String[] list, String start, boolean number, boolean nextLine) {
        if (!new Strings().isNullOrEmpty(list)) {
            for (int i = 0; i < list.length; i++) {
                if (!new Strings().isNullOrEmpty(start)) {
                    this.printLine(start, false);
                }

                if (number) {
                    this.printLine("[" + (i + 1) + "] ", false);
                }

                this.printLine(list[i], nextLine);
            }
        }
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getLine() {
        return this.line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public boolean isAnimation() {
        return this.animation;
    }

    public void setAnimation(boolean animation) {
        this.animation = animation;
    }
}
