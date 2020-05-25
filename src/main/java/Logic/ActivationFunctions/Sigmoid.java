package Logic.ActivationFunctions;

/**
 * Represents classic sigmoid function, i.e. the mapping f(x)= 1/(1+exp(-x))
 */
public class Sigmoid extends ActivationFunction {
    @Override
    public float calculate(float argument) {
        return 1.0f/(1.0f+(float)Math.exp(-argument));
    }

    @Override
    public float calculateDerivative(float argument) {
        float outcome = calculate(argument);
        return outcome*(1-outcome);
    }
}
