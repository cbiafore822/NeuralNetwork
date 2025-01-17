import java.util.ArrayList;

/**
 * @author Cole Biafore
 * @version 1.0
 */
public class InputNeuron extends Neuron {
    protected ArrayList<NeuralConnection> outputs;

    /**
     * Creates an Input Neuron
     * @param value value calculated that is outputted to the next neuron
     * @param outputs ArrayList of Neural Connections from this neuron
     */
    public InputNeuron(double value, ArrayList<NeuralConnection> outputs) {
        this.error = 0;
        this.value = value;
        this.outputs = outputs;
    }

    /**
     * Creates an Input Neuron with a default value of 0.5
     * @param outputs ArrayList of Neural Connections from this neuron
     */
    public InputNeuron(ArrayList<NeuralConnection> outputs) {
        this(0.5, outputs);
    }


    /**
     * Sets the value for this input Neuron
     * @param value value for the neuron to be set to
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * gets the outputs to this neuron
     * @return ArrayList of Neural Connections from this neuron
     */
    public ArrayList<NeuralConnection> getOutputs() {
        return outputs;
    }
}
