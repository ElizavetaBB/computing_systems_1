package org.example;

public class Layer {
    private Neuron neurons[];
    private double[] outputs;

    private double[] backPropagationDelta;
    public Layer(int neuronNumbers, int previousNeuronNumbers) {
        neurons = new Neuron[neuronNumbers];
        for (int i = 0; i < neuronNumbers; i++) {
            double[] weights = new double[previousNeuronNumbers];
            for (int j = 0; j < weights.length; j++) {
                weights[j] = Math.random() * 0.1;
            }
            neurons[i] = new Neuron(weights);
        }
    }

    public double[] getResults() {
        return outputs;
    }

    public double[] getBackPropagationDelta() {
        return backPropagationDelta;
    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    public double[] getOutputs(double[] inputs) {
        outputs = new double[neurons.length];
        for (int i = 0; i < neurons.length; i++) {
            outputs[i] = neurons[i].setOutput(inputs);
        }
        return outputs;
    }

    public void backPropagationLastLayer(Layer previousLayer, double[] expected, double[] results, double teachAlpha) {
        backPropagationDelta = new double[results.length];
        for (int i = 0; i < backPropagationDelta.length; i++) {
            backPropagationDelta[i] = results[i] * (1 - results[i]) * (expected[i] - results[i]);
            double[] weights = neurons[i].getWeights();
            double[] previousLayerOutputs = previousLayer.getResults();
            double[] deltaWeights = new double[weights.length];
            for (int j = 0; j < deltaWeights.length; j++) {
                deltaWeights[j] = teachAlpha * backPropagationDelta[i] * previousLayerOutputs[j];
            }
            neurons[i].setDeltaWeights(deltaWeights);
        }
    }

    public void backPropagation(Layer nextLayer, double[] previousOutputs, double teachAlpha) {
        backPropagationDelta = new double[neurons.length];
        for (int i = 0; i < backPropagationDelta.length; i++) {
            double deltaW = 0;
            double[] nextLayersPropagationDeltas = nextLayer.getBackPropagationDelta();
            for (int j = 0; j < nextLayersPropagationDeltas.length; j++) {
                deltaW += nextLayersPropagationDeltas[j] * nextLayer.getNeurons()[j].getWeights()[i];
            }
            backPropagationDelta[i] = outputs[i] * (1 - outputs[i]) * deltaW;
            double[] weights = neurons[i].getWeights();
            double[] deltaWeights = new double[weights.length];
            for (int j = 0; j < deltaWeights.length; j++) {
                deltaWeights[j] = teachAlpha * backPropagationDelta[i] * previousOutputs[j];
            }
            neurons[i].setDeltaWeights(deltaWeights);
        }
    }

    public void addDeltaWeight() {
        for (Neuron neuron: neurons) {
            neuron.addDeltaWeight();
        }
    }
}
