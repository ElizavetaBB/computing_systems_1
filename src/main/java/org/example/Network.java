package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Network {
    private final int imageSize = 49;
    public Layer[] layers;

    public double[] inputs;

    double teachAlpha;

    public Network(int[] neuronNumbers, double teachAlpha) {
        layers = new Layer[neuronNumbers.length + 1];
        for (int i = 0; i < neuronNumbers.length; i++) {
            int previousNeuronNumbers = i == 0? imageSize: neuronNumbers[i - 1];
            layers[i] = new Layer(neuronNumbers[i], previousNeuronNumbers);
        }
        layers[neuronNumbers.length] = new Layer(3, neuronNumbers[neuronNumbers.length - 1]);
        this.teachAlpha = teachAlpha;
    }

    public double[] getOutputs(double[] inputs) {
        Map<Integer, double[]> neuronInputs = new HashMap<>();
        for (int i = 0; i < layers.length; i++) {
            double[] output;
            if (i == 0) {
                output = layers[i].getOutputs(inputs);
            } else {
                output = layers[i].getOutputs(neuronInputs.get(i-1));
            }
            neuronInputs.put(i, output);
        }
        return neuronInputs.get(layers.length-1);
    }

    public double[] runImage(String imagePath) throws IOException {
        inputs = new double[49];
        BufferedImage image = ImageIO.read(new File(imagePath));

        for( int i = 0; i < 7; i++)
            for (int j = 0; j < 7; j++) {
                int color = image.getRGB(i, j);
                inputs[i * 7 + j] = Math.abs(((color & 0xff00) >> 8) - 255) * 1.0 / 255;
            }

        return getOutputs(inputs);
    }

    public double[] runArray(double[] trainData) {
        inputs = new double[49];

        for( int i = 0; i < 7; i++)
            for (int j = 0; j < 7; j++) {
                inputs[i * 7 + j] = trainData[i * 7 + j];
            }

        return getOutputs(trainData);
    }

    public double countError(double[] expected, double[] results) {
        double sum = 0;
        for (int i = 0; i < results.length; i++) {
            sum += Math.abs((expected[i] - results[i]));
        }
        return sum / 2;
    }

    public void backPropagation(double[] expected, double[] results, double[] inputs, double teachAlpha) {
        Layer lastLayer = layers[layers.length - 1];
        lastLayer.backPropagationLastLayer(layers[layers.length - 2], expected, results, teachAlpha);
        for (int i = layers.length - 2; i >= 0; i--) {
            if (i == 0) {
                layers[i].backPropagation(layers[i + 1], inputs, teachAlpha);
            } else {
                layers[i].backPropagation(layers[i + 1], layers[i - 1].getResults(), teachAlpha);
            }
        }
        for (Layer layer: layers) {
            layer.addDeltaWeight();
        }
    }
}
