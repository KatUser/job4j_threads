package ru.job4j.concurrent;

import java.io.*;
import java.net.*;
import java.nio.file.Files;

public class Wget2 implements Runnable {
    public final String url;
    private final int speed;

    public Wget2(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        long startAt = System.currentTimeMillis();
        File file = new File("testFile.txt");
        try (InputStream inputStream = new URL(url).openStream();
            FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            System.out.println("Opening connection to URL: "
                    + (System.currentTimeMillis() - startAt) + " ms.");
            byte[] dataBuffer = new byte[speed];
            int bytesRead;
            while ((bytesRead = inputStream.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                long timeWhenStartedLoading = System.nanoTime();
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long timeWhenLoaded = System.nanoTime();
                long diffTime = timeWhenLoaded - timeWhenStartedLoading;
                System.out.println("Bytes read : "
                        + bytesRead
                        + ", in "
                        + (diffTime)
                        + " nanos.");
                long bytesReadPerMs = bytesRead * 1_000_000L / diffTime;
                if (bytesReadPerMs  >= speed) {
                    long sleepTime = ((bytesReadPerMs) / speed);
                    System.out.println("The thread is sleeping for "
                            + sleepTime
                            + " milliseconds."
                    );
                    Thread.sleep(sleepTime);
                }
            }
            System.out.println(Files.size(file.toPath()) + " bytes");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void validateArguments(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Not enough arguments passed to the app.");
        }
        if (args[0].length() < 1 || args[1].length() < 1) {
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
       int speed = Integer.parseInt(args[1]);
       Thread wget = new Thread(new Wget2(url, speed));
       wget.start();
       wget.join();
    }
}
