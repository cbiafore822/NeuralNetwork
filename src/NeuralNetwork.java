import java.util.ArrayList;

/**
 * @author Cole Biafore
 * @version 1.0
 */
public class NeuralNetwork {
    protected double learningRate;
    protected InputNeuron[] inputs;
    protected HiddenNeuron[][] hidden;
    protected OutputNeuron[] outputs;

    /**
     * Creates a Neural Network with random weights and biases
     * @param layers int array representing the number of neurons in each layer
     * @param learningRate rate at which the Neural Network Learns
     * @throws IllegalArgumentException thrown if not enough layers or Learning Rate is not a percentage
     */
    public NeuralNetwork(int[] layers, double learningRate) {
        if (layers.length < 2 || learningRate < 0 || learningRate > 1) {
            throw new IllegalArgumentException("Invalid Neural Network instantiation");
        }
        // Instantiates each layers of the Network
        this.learningRate = learningRate;
        inputs = new InputNeuron[layers[0]];
        hidden = new HiddenNeuron[layers.length - 2][];
        for (int i = 1; i < layers.length - 1; i++) {
            hidden[i - 1] = new HiddenNeuron[layers[i]];
        }
        outputs = new OutputNeuron[layers[layers.length - 1]];

        NeuralConnection connection;
        // Creates each Neuron and connects them accordingly
        // Checks to see if this Neural Network is a Multi-Layered Network
        if (layers.length > 2) {
            for (int i = 0; i < inputs.length; i++) {
                inputs[i] = new InputNeuron(new ArrayList<>(hidden[0].length));
            }
            // Connects the first Hidden Layer to the Input Layer
            for (int i = 0; i < hidden[0].length; i++) {
                hidden[0][i] = new HiddenNeuron(new ArrayList<>(inputs.length),
                        new ArrayList<>(layers.length > 3 ? hidden[1].length : outputs.length));
                for (int j = 0; j < inputs.length; j++) {
                    connection = new NeuralConnection(inputs[j], hidden[0][i]);
                    inputs[j].getOutputs().add(connection);
                    hidden[0][i].getInputs().add(connection);
                }
            }

            // Connects the Hidden Layers
            for (int i = 1; i < hidden.length; i++) {
                for (int j = 0; j < hidden[i].length; j++) {
                    hidden[i][j] = new HiddenNeuron(new ArrayList<>(hidden[i - 1].length),
                            i == hidden.length - 1 ? new ArrayList<>(outputs.length) :
                                    new ArrayList<>(hidden[i + 1].length));
                    for (int k = 0; k < hidden[i - 1].length; k++) {
                        connection = new NeuralConnection(hidden[i - 1][k], hidden[i][j]);
                        hidden[i - 1][k].getOutputs().add(connection);
                        hidden[i][j].getInputs().add(connection);
                    }
                }
            }

            // Connects the Output Layer to the last Hidden Layer
            for (int i = 0; i < outputs.length; i++) {
                outputs[i] = new OutputNeuron(new ArrayList<>(hidden[hidden.length - 1].length));
                for (int j = 0; j < hidden[hidden.length - 1].length; j++) {
                    connection = new NeuralConnection(hidden[hidden.length - 1][j], outputs[i]);
                    hidden[hidden.length - 1][j].getOutputs().add(connection);
                    outputs[i].getInputs().add(connection);
                }
            }
        } else {
            for (int i = 0; i < inputs.length; i++) {
                inputs[i] = new InputNeuron(new ArrayList<>(outputs.length));
            }
            // Connects the Output Layer to the Input Layer
            for (int i = 0; i < outputs.length; i++) {
                outputs[i] = new OutputNeuron(new ArrayList<>(inputs.length));
                for (int j = 0; j < inputs.length; j++) {
                    connection = new NeuralConnection(inputs[j], outputs[i]);
                    inputs[j].getOutputs().add(connection);
                    outputs[i].getInputs().add(connection);
                }
            }
        }
    }

    /**
     * Classifies a given set of inputs
     * @param inputVals a double array of values between 0.0 and 1.0 to represent the first input layer
     * @return a double array of the Output Neuron's values
     * @throws IllegalArgumentException thrown if the input Values does not equal the number of Neurons in the Input
     * Layer
     */
    public double[] classify(double[] inputVals) {
        if (inputVals.length != inputs.length) {
            throw new IllegalArgumentException("Input data should be scaled for this Neural Network");
        }
        double[] res = new double[outputs.length];
        for (int i = 0; i < inputs.length; i++) {
            inputs[i].setValue(inputVals[i]);
        }
        for (int i = 0; i < hidden.length; i++) {
            for (int j = 0; j < hidden[i].length; j++) {
                hidden[i][j].calculateValue();
            }
        }
        for (int i = 0; i < outputs.length; i++) {
            outputs[i].calculateValue();
            res[i] = outputs[i].getValue();
        }
        return res;
    }

    /**
     * Algorithm that calculates the error values and updates the weights and biases in the Neural Network (training)
     * @param real Excpected results
     */
    protected void backProp(double[] real) {
        for (int i = 0; i < outputs.length; i++) {
            outputs[i].calculateError(real[i]);
        }
        for (int i = hidden.length - 1; i >= 0; i--) {
            for (int j = 0; j < hidden[i].length; j++) {
                hidden[i][j].calculateError();
            }
        }
        for (int i = 0; i < hidden.length; i++) {
            for (int j = 0; j < hidden[i].length; j++) {
                hidden[i][j].updateWB(learningRate);
            }
        }
        for (int i = 0; i < outputs.length; i++) {
            outputs[i].updateWB(learningRate);
        }
    }
}
