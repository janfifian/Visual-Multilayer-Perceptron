package Logic.ActivationFunctions;

/**
 * An abstract class representing the activation function of a neuron.
 */
public abstract class ActivationFunction {

    /**
     * Returns the value of the activation function for a given argument.
     * @param argument "x" argument for the activation function f
     * @return f(x) -- the value of activation function for provided argument
     */
    public abstract float calculate(float argument);

    /**
     * Returns the value of the derivative of the activation function for a given argument.
     * @param argument "x" argument for the derivative of the activation function f
     * @return f(x) -- the value of the derivative of the activation function for provided argument
     */
    public abstract float calculateDerivative(float argument);

}
