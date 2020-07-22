import java.util.ArrayList;
import java.util.Random;

/**
 * @author Cole Biafore
 * @version 1.0
 */
public class OutputNeuron extends Neuron {
    protected ArrayList<NeuralConnection> inputs;
    protected double bias;

    /**
     * Creates an Output Neuron
     * @param error value used to calculate how far off from expectation the neuron is preforming
     * @param value value calculated that is outputted from this Neuron
     * @param inputs ArrayList of Neural Connections to this neuron
     * @param bias bias to this neuron
     */
    public OutputNeuron(double error, double value, ArrayList<NeuralConnection> inputs, double bias) {
        this.error = error;
        this.value = value;
        this.inputs = inputs;
        this.bias = bias;
    }

    /**
     * Creates an Output Neuron with a random bias
     * @param inputs ArrayList of Neural Connections to this neuron
     */
    public OutputNeuron(ArrayList<NeuralConnection> inputs) {
        this(0, 0.5, inputs, new Random().nextDouble() * 2 - 1);
    }

    /**
     * gets the inputs to this neuron
     * @return ArrayList of Neural Connections to this neuron
     */
    public ArrayList<NeuralConnection> getInputs() {
        return inputs;
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
     * @param exp the expected value
     * @return a double representing the error for this neuron
     */
    public double calculateError(double exp) {
        double diff = exp - value;
        error = diff * actFuncDeriv(value);
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
