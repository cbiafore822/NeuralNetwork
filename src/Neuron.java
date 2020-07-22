/**
 * @author Cole Biafore
 * @version 1.0
 */
public abstract class Neuron  {
    protected double error;
    protected double value;

    /**
     * gets the error from this Neuron
     * @return a double representing the error
     */
    public double getError() {
        return error;
    }

    /**
     * gets the value from this neuron
     * @return a double representing the value
     */
    public double getValue() {
        return value;
    }

}