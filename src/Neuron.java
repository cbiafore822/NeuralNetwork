/**
 * @author Cole Biafore
 * @version 1.0
 */
public abstract class Neuron  {

    /**
     * gets the error from this Neuron
     * @return a double representing the error
     */
    public abstract double getError();

    /**
     * gets the value from this neuron
     * @return a double representing the value
     */
    public abstract double getValue();

}