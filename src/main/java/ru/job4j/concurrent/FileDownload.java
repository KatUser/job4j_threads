package ru.job4j.concurrent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

public class FileDownload {
    public static void main(String[] args) throws Exception {
        var startAt = System.currentTimeMillis();
        var file = new File("tmp.xml");
        String url = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        try (InputStream input = new URL(url).openStream();
             /* URL openStream initiates tcp connection to url, http GET request is sent,
             * if 200 OK, then server sends back HTTP response */
            var output = new FileOutputStream(file)) {
            /* need to read the bytes from the InputStream that
            the openStream() method returns in order to retrieve the data payload into the program*/
            System.out.println("Open connection: "
                        + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[512]; /* запись сюда */
            int bytesRead;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var downloadAt = System.nanoTime();
                output.write(dataBuffer, 0, bytesRead);
                System.out.println("Read 512 bytes : " + (System.nanoTime() - downloadAt) + " nano.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Files.size(file.toPath()) + " bytes.");
    }
}
