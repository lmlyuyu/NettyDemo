package com.maolin.io;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class EchoPrinter {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(System.in), StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(System.out), StandardCharsets.UTF_8))) {
            writer.write(reader.readLine());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
