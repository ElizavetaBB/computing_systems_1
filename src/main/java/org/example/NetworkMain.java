package org.example;

import java.io.*;
import java.util.*;

public class NetworkMain {
    public static void main(String[] args) throws IOException {
        Map<Integer, double[]> trainData = new HashMap<>();
        Map<Integer, double[]> resultsData = new HashMap<>();
        try {
            File file = new File("src/main/resources/settings/label_array_data.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            int lineOrder = 0;
            int size = 0;
            while (line != null) {
                if (lineOrder == 0) {
                    String[] trainDataLine = line.split(";");
                    double[] data = new double[49];
                    for (int i = 0; i < 49; i++) {
                        data[i] = Double.parseDouble(trainDataLine[i]);
                    }
                    trainData.put(size, data);
                    line = reader.readLine();
                    lineOrder = 1;
                } else {
                    String[] resultsDataLine = line.split(";");
                    double[] results = new double[3];
                    for (int i = 0; i < 3; i++) {
                        results[i] = Double.parseDouble(resultsDataLine[i]);
                    }
                    resultsData.put(size, results);
                    line = reader.readLine();
                    lineOrder = 0;
                    size++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        int[] neurons = null;
        double teachAlpha = 0;
        double accurance = 0;
        int epoches = 0;
        int batchSize = 1;
        try {
            File file = new File("src/main/resources/settings/settings.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            int i = 0;
            while (line != null) {
                if (i == 0) {
                    String[] trainDataLine = line.split(";");
                    neurons = new int[trainDataLine.length];
                    for (int j = 0; j < neurons.length; j++) {
                        neurons[j] = Integer.parseInt(trainDataLine[j]);
                    }
                    i++;
                } else if (i == 1) {
                    teachAlpha = Double.parseDouble(line);
                    i++;
                } else if (i == 2) {
                    accurance = Double.parseDouble(line);
                    i++;
                } else if (i == 3) {
                    epoches = Integer.parseInt(line);
                    i++;
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        Network network = new Network(neurons, teachAlpha);
        long startTime = System.nanoTime();
        for (int i = 0; i < epoches; i++) {
            List<Integer> trainValues = new ArrayList<>();
            for (int j = 0; j < trainData.size(); j++) {
                trainValues.add(j);
            }
            Collections.shuffle(trainValues);
            double allError = 0;
            for (int j = 0; j < (1500 / batchSize); j++) {
                double sumError = 0;
                double[] results = null;
                for (int k = 0; k < batchSize; k++) {
                    results = network.runArray(trainData.get(trainValues.get(j * batchSize + k)));
                    double error = network.countError(resultsData.get(trainValues.get(j * batchSize + k)), results);
                    allError += error;
                    sumError += error;
                }
                if ((sumError / batchSize) > accurance) {
                    network.backPropagation(
                            resultsData.get(trainValues.get(j * batchSize + batchSize - 1)), results, network.inputs, teachAlpha
                    );
                }
            }
            System.out.println(i + " epoch error = " + (allError / trainData.size()));
            if ((allError / trainData.size()) <= accurance) {
                break;
            }
        }
        long endTime = System.nanoTime();
        System.out.println("Learning Time: " + (endTime - startTime) / 1000000 + " msec");

        System.out.println("----------------------\nTest data good");
        String[] testImages = {"circle_1.png", "square_1.png", "triangle_1.png"};
        for (String image : testImages) {
            startTime = System.nanoTime();
            double[] results = network.runImage("src/main/resources/" + image);
            endTime = System.nanoTime();

            System.out.println(image + " results: [" + results[0] + "; " + results[1] + "; " + results[2] + "]");
            if (results[0] > results[1]) {
                if (results[0] > results[2]) {
                    System.out.println("circle");
                } else {
                    System.out.println("triangle");
                }
            } else {
                if (results[1] > results[2]) {
                    System.out.println("square");
                } else {
                    System.out.println("triangle");
                }
            }
            System.out.println("Output Time: " + (endTime - startTime) / 1000000 + " msec");
            System.out.println(" ");
        }
        System.out.println("----------------------\nTest data noisy");
        String[] testImagesNoise = {
                "noisy/triangle_5.png",
                "noisy/square_5.png",
                "noisy/circle_5.png",
                "noisy/square_3.png",
                "noisy/circle_1_test.png",
                "noisy/triangle_7.png",
                "noisy/triangle_test.png",
                "noisy/circle_8.png",
                "noisy/circle_3.png",
                "noisy/square_2.png",
                "noisy/triangle_3.png",
                "noisy/square_7.png",
                "noisy/circle_4.png",
                "noisy/square_4.png",
                "noisy/square_8.png",
                "noisy/circle_7.png",
                "noisy/triangle_2.png",
                "noisy/triangle_8.png",
                "noisy/triangle_6.png",
                "noisy/circle_square.png",
                "noisy/circle_2.png"};
        for (String image : testImagesNoise) {
            startTime = System.nanoTime();
            double[] results = network.runImage("src/main/resources/" + image);
            endTime = System.nanoTime();
            System.out.println(image + " results: [" + results[0] + "; " + results[1] + "; " + results[2] + "]");
            if (results[0] > results[1]) {
                if (results[0] > results[2]) {
                    System.out.println("circle");
                } else {
                    System.out.println("triangle");
                }
            } else {
                if (results[1] > results[2]) {
                    System.out.println("square");
                } else {
                    System.out.println("triangle");
                }
            }
            System.out.println("Output Time: " + (endTime - startTime) / 1000000 + " msec");
            System.out.println(" ");
        }

    }
}