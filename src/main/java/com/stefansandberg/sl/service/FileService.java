package com.stefansandberg.sl.service;

import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileService {

    public void saveToFile(String data, String filename) {
        try {
            Files.writeString(Paths.get(filename), data);
            System.out.println("Data saved to: " + filename);
        } catch (Exception e) {
            System.err.println("Error saving file: " +  e.getMessage());
        }
    }

    public String readFromFile(String filename) {
        try {
            return Files.readString(Paths.get(filename));
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }
    }
}
