package ru.job4j.concurrent;

import java.io.*;
import java.net.*;
import java.nio.file.Files;

public class Wget2 implements Runnable {
    public final String url;
    public final File file;
    private final int speed;

    public Wget2(String url, File file, int speed) {
        this.url = url;
        this.file = file;
        this.speed = speed;
    }

    @Override
    public void run() {
        long startAt = System.currentTimeMillis();
        try (InputStream inputStream = new URL(url).openStream();
            FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            System.out.println("Opening connection to URL: "
                    + (System.currentTimeMillis() - startAt) + " ms.");

            byte[] dataBuffer = new byte[speed];
            long timeWhenBytesReceivedReachedSpeed = 0; /* когда сумма полученных байт = speed */
            int allBytesReceived = 0; /*  общее число скачанных байт */
            int bytesReadAtATime; /* кол-во байт, скачанных за раз */
            long startTime = System.currentTimeMillis();

            while ((bytesReadAtATime = inputStream.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesReadAtATime);
                allBytesReceived += bytesReadAtATime;

                if (allBytesReceived >= speed) { /*  проверка кол-ва байт */
                    timeWhenBytesReceivedReachedSpeed += (System.currentTimeMillis() - startTime);
                    if (timeWhenBytesReceivedReachedSpeed <= 1_000) {
                        System.out.println("The thread is sleeping for "
                                + (timeWhenBytesReceivedReachedSpeed)
                                + " milliseconds."
                        );
                        Thread.sleep(timeWhenBytesReceivedReachedSpeed);
                        timeWhenBytesReceivedReachedSpeed = 0;
                        allBytesReceived = 0;
                        startTime = System.currentTimeMillis();
                    }
                }
            }
            System.out.println(Files.size(file.toPath()) + " bytes");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void validateArguments(String[] args) throws IOException {
        if (args.length < 3) {
            System.out.println("Not enough arguments passed to the app.");
        }
        if (args[0].length() < 1 || args[1].length() < 1 || args[2].length() < 1) {
            System.out.println("Incorrect arguments are passed to the app.");
        }
        URL url = new URL(args[0]);
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        int responseCode = huc.getResponseCode();
        if (HttpURLConnection.HTTP_OK != responseCode) {
            System.out.println("The destination url cannot be reached.");
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
       validateArguments(args);
       String url = args[0];
       File file = new File(args[1]);
       int speed = Integer.parseInt(args[2]);
       Thread wget = new Thread(new Wget2(url, file, speed));
       wget.start();
       wget.join();
    }
}
