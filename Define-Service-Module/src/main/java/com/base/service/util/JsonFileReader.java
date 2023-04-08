package com.base.service.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class JsonFileReader {
    public String getRequestFile(String path) {
        return readFile("requests/" + path);
    }
    public String getResponseFile(String path) {
        return readFile("responses/" + path);
    }
    public String readFile(String path) {
        try (InputStream ips = getClass().getClassLoader().getResourceAsStream(path);
             BufferedReader buffReader = new BufferedReader(new InputStreamReader(ips));
        ) {
            String line;
            StringBuilder stringBuffer = new StringBuilder();
            while ((line = buffReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            return stringBuffer.toString();
        } catch (IOException | NullPointerException ex) {
            throw new IllegalArgumentException("Please check file in " + path);
        }
    }
}
