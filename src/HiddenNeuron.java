import java.util.ArrayList;
import java.util.Random;

/**
 * @author Cole Biafore
 * @version 1.0
 */
public class HiddenNeuron extends Neuron {
    protected double error;
    protected double value;
    protected ArrayList<NeuralConnection> inputs;
    protected ArrayList<NeuralConnection> outputs;
    protected double bias;

    /**
     * Creates a Hidden Neuron
     * @param error value used to calculate how far off from expectation the neuron is preforming
     * @param value value calculated that is outputted to the next neuron
     * @param inputs ArrayList of Neural Connections to this neuron
     * @param outputs ArrayList of Neural Connections from this neuron
     * @param bias bias to this neuron
     */
    public HiddenNeuron(double error, double value, ArrayList<NeuralConnection> inputs,
                        ArrayList<NeuralConnection> outputs, double bias) {
        this.error = error;
        this.value = value;
        this.inputs = inputs;
        this.outputs = outputs;
        this.bias = bias;
    }

    /**
     * Creates a Hidden Neuron with a random bias
     * @param inputs ArrayList of Neural Connections to this neuron
     * @param outputs ArrayList of Neural Connections from this neuron
     */
    public HiddenNeuron(ArrayList<NeuralConnection> inputs, ArrayList<NeuralConnection> outputs) {
        this(0, 0.5, inputs, outputs, new Random().nextDouble() * 2 - 1);
    }

    /**
     * gets the inputs to this neuron
     * @return ArrayList of Neural Connections to this neuron
     */
    public ArrayList<NeuralConnection> getInputs() {
        return inputs;
    }

    /**
     * gets the error from this Neuron
     * @return a double representing the error
     */
    @Override
    public double getError() {
        return error;
    }

    /**
     * gets the value from this neuron
     * @return a double representing the value
     */
    @Override
    public double getValue() {
        return value;
    }

    /**
     * gets the outputs to this neuron
     * @return ArrayList of Neural Connections from this neuron
     */
    public ArrayList<NeuralConnection> getOutputs() {
        return outputs;
    }

    /**
     * gets the bias from this neuron
     * @return double representing the bias for this neuron
     */
    public double getBias() {
        return bias;
    }

    /**
     * calculates the value for this neuron
     * @return the value of the neuron
     */
    public double calculateValue() {
        double sum = 0;
        for (NeuralConnection connection : inputs) {
            sum += (connection.getPrevNeuron().getValue() * connection.getWeight());
        }
        sum += bias;
        value = actFunc(sum);
        return value;
    }

    /**
     * Activation function used for this neuron
     * @param input the sum of the weights and biases inputted to this neuron
     * @return a double representing the value from this function
     */
    private double actFunc(double input) {
        return 1 / (1 + Math.pow(Math.E, input * -1));
    }

    /**
     * calculates the error of this neuron from the expected values
     * @return a double representing the error for this neuron
     */
    protected double calculateError() {
        double sum = 0;
        for (NeuralConnection output : outputs) {
            sum += output.getWeight() * output.getNextNeuron().getError();
        }
        error = sum * actFuncDeriv(value);
        return error;
    }

    /**
     * derivative of the Activation Function
     * @param input input for the derivative
     * @return double representing the value from this derivative
     */
    private double actFuncDeriv(double input) {
        return input * (1 - input);
    }

    /**
     * updates the weights and biases for this Neuron
     * @param learningRate Learning Rate for the Neural Network
     */
    public void updateWB(double learningRate) {
        for (NeuralConnection input : inputs) {
            input.setWeight(input.getWeight() + learningRate * input.getPrevNeuron().getValue() * error);
        }
        bias += learningRate * error;
    }
}
