package Logic.ActivationFunctions;

/**
 * Represents the Rectified Linear Unit Function, i.e. f(x)=x for x>0 and 0 otherwise.
 */
public class ReLU extends ActivationFunction {
    @Override
    public float calculate(float argument) {
        return argument>0 ? argument : 0.0f;
    }

    @Override
    public float calculateDerivative(float argument) {
        return argument>0 ? 1.0f : 0.0f;
    }
}
