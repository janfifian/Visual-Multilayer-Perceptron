package Logic.ActivationFunctions;

/**
 * Represents the identity function, i.e. f(x)=x.
 */
public class Identity extends ActivationFunction {
    @Override
    public float calculate(float argument) {
        return argument;
    }

    @Override
    public float calculateDerivative(float argument) {
        return 1.0f;
    }
}
