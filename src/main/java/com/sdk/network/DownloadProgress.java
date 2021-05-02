package com.sdk.network;

import com.sdk.console.Console;
import com.sdk.storage.base.FileOperation;
import com.sdk.storage.file.SingleFile;
import java.util.Objects;

public class DownloadProgress implements Runnable {

    private long size;
    private FileOperation file;

    public DownloadProgress(long size, FileOperation file) {
        this.size = size;
        this.file = file;
    }

    @Override
    public void run() {
        long currentProgress = 0, nextProgress = 0;
        System.out.print("[");

        try {
            if (Objects.isNull(file)) {
                return;
            } else {
                if (file.exists()) {
                    if (file.getSize() == size) {
                        new Console().printCharacters('-', 50, false);
                        System.out.println("]");
                        return;
                    }
                }
            }

            while (true) {
                if (file.exists()) {
                    currentProgress = (file.getSize() * 100) / size;

                    if (currentProgress != nextProgress) {
                        nextProgress = currentProgress;

                        if (nextProgress % 2 == 0) {
                            System.out.print("-");
                        }
                    }

                    if (size == file.getSize()) {
                        break;
                    }
                }
            }

            System.out.println("]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
