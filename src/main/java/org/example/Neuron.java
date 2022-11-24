package org.example;

public class Neuron {
    private double[] weights;
    private double output;
    private double[] deltaWeights;
    public Neuron(double weights[]) {
        this.weights = new double[weights.length];
        for (int i = 0; i < weights.length; i++) {
            this.weights[i] = weights[i];
        }
    }

    public double[] getWeights() {
        return weights;
    }

    public void addDeltaWeight() {
        for (int i = 0; i < weights.length; i++) {
            weights[i] += deltaWeights[i];
        }
    }

    public void setDeltaWeights(double[] deltaWeights) {
        this.deltaWeights = new double[deltaWeights.length];
        for (int i = 0; i < deltaWeights.length; i++) {
            this.deltaWeights[i] = deltaWeights[i];
        }
    }

    public double setOutput(double[] x) {
        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += x[i] * weights[i];
        }
        output = 1.0/(1+Math.exp(-sum));
        return output;
    }
}
