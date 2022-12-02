package org.example.generator;

import java.io.IOException;

public class GeneratorMain {
    public static void main(String[] args) throws IOException {
        Generator generator = new Generator(1500, 4);
        generator.printResults("src/main/resources/label_array_data.txt");
    }
}
