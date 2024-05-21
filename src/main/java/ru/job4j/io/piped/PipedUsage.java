package ru.job4j.io.piped;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedUsage {
    public static void main(String[] args) throws Exception {
        Thread firstThread;
        Thread secondThread;
        try (PipedInputStream inputStream = new PipedInputStream()) {
            final PipedOutputStream outputStream = new PipedOutputStream();

            firstThread = new Thread(
                    () -> {
                        try {
                            outputStream.write("Job4j".getBytes());
                            outputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            );

            secondThread = new Thread(
                    () -> {
                        try {
                            int character;
                            while ((character = inputStream.read()) != -1) {
                                System.out.println((char) character);
                            }
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );

            inputStream.connect(outputStream);
        }
        firstThread.start();
        secondThread.start();
    }
}
