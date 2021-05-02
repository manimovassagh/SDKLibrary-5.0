package com.sdk.data.types;

import static com.sdk.tools.ExternalTools.toClockTime;

public class Numbers {

    public long generateRandomNumber(long min, long max) {
        return (long) ((Math.random() * (max - min)) + min);
    }

    public boolean isPerfect(int number) {
        int sum = 1;

        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                if (i * i != number)
                    sum = sum + i + number / i;
                else
                    sum = sum + i;
            }
        }

        return sum == number && number != 1;
    }

    public boolean isOdd(int number) {
        return number % 2 != 0;
    }

    public boolean isEven(int number) {
        return number % 2 == 0;
    }
}
