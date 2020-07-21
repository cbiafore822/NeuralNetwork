import java.util.Random;

public class NeuralConnection {
    protected double weight;
    protected Neuron prevNeuron;
    protected Neuron nextNeuron;

    /**
     * Creates a Neural Connection
     * @param weight weight of the connection
     * @param prevNeuron Neuron from the previous layer
     * @param nextNeuron Neuron from the next layer
     */
    public NeuralConnection(double weight, Neuron prevNeuron, Neuron nextNeuron) {
        this.weight = weight;
        this.prevNeuron = prevNeuron;
        this.nextNeuron = nextNeuron;
    }

    /**
     * Creates a Neural Connection with a random weight
     * @param prevNeuron Neuron from the previous layer
     * @param nextNeuron Neuron from the next layer
     */
    public NeuralConnection(Neuron prevNeuron, Neuron nextNeuron) {
        this(new Random().nextDouble() * 2 - 1, prevNeuron, nextNeuron);
    }

    /**
     * Returns the weight of the Neural Connection
     * @return weight as a double
     */
    public double getWeight() {
        return weight;
    }

    /**
     * sets the weight of the Neural Connection
     * @param weight a double representing the weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * gets the Neuron from the previous layer
     * @return
     */
    public Neuron getPrevNeuron() {
        return prevNeuron;
    }

    /**
     * gets the Neuron from the next layer
     * @return
     */
    public Neuron getNextNeuron() {
        return nextNeuron;
    }
}
