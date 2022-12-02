package org.example.generator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {

    private Map<Integer, double[]> results = new HashMap<>();

    private final double[] circle = {
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0,
            0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0,
            0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0,
            0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0,
            0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

    private final double[] square = {
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0,
            0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0,
            0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0,
            0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0,
            0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

    private final double[] triangle = {
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0,
            0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0,
            1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
            1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

//    private final double[] rhombus = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
//            0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,
//            0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0,
//            0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0,
//            0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0,
//            0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,
//            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

    public void printResults(String path) throws IOException {
        try (FileWriter writer = new FileWriter(path, false)) {
            for (int i = 0; i < results.size(); i++) {
                writer.write("" + results.get(i)[0]);
                for (int j = 1; j < circle.length; j++) {
                    writer.write(";" + results.get(i)[j]);
                }
                writer.append('\n');
                writer.write("" + results.get(i)[49]);
                for (int j = 50; j < 52; j++) {
                    writer.write(";" + results.get(i)[j]);
                }
                writer.append('\n');
            }
        }
    }

    private double[] makeResult(double[] standard, int noise) {
        double[] result = new double[standard.length + 3];

        for (int i = 0; i < standard.length; i++) {
            result[i] = standard[i];
        }

        for (int i = 0; i < noise; i++) {
            int k = (int) (Math.random() * 48);
            while (Math.abs(result[k] - standard[k]) >= 0.000001) {
                k = (int) (Math.random() * 48);
            }
            result[k] = Math.abs(standard[k] - 1.0) >= 0.000001 ? 1.0 : 0.0;
        }

        int circleProbability = 49;
        int squareProbability = 49;
        int triangleProbability = 49;
//        int rhombusProbability = 49;

        for (int i = 0; i < 49; i++) {
            if (Math.abs(result[i] - circle[i]) >= 0.000001) circleProbability--;
            if (Math.abs(result[i] - square[i]) >= 0.000001) squareProbability--;
            if (Math.abs(result[i] - triangle[i]) >= 0.000001) triangleProbability--;
//            if (Math.abs(result[i] - rhombus[i]) >= 0.000001) rhombusProbability--;
        }

        result[49] = (circleProbability * 1.0) / 49;
        result[50] = (squareProbability * 1.0) / 49;
        result[51] = (triangleProbability * 1.0) / 49;

//        result[51] = (rhombusProbability * 1.0) / 49;

        return result;
    }

    public Generator(int size, int noise) {
        int resultsCount = 0;
        int maxNoise = noise;

        while (resultsCount < size) {
            if (resultsCount >= (size - 300)) {
                noise = 0;
            } else {
                noise = ThreadLocalRandom.current().nextInt(1, maxNoise + 1);
            }

            double[] resultCircle = makeResult(circle, noise);
            double[] resultSquare = makeResult(square, noise);
            double[] resultTriangle = makeResult(triangle, noise);
//            double[] resultRhombus = makeResult(rhombus, noise);

            results.put(resultsCount, resultCircle);
            resultsCount++;
            results.put(resultsCount, resultSquare);
            resultsCount++;
            results.put(resultsCount, resultTriangle);
//            results.put(resultsCount, resultRhombus);
            resultsCount++;
        }
    }
}