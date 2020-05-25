package Logic.ActivationFunctions;

import static java.lang.Math.tanh;

/**
 * Represents hyperbolic tangent i.e. f(x)=(exp(2x)-1)/(exp(2x)+1).
 */
public class Tanh extends ActivationFunction {
    @Override
    public float calculate(float argument) {
        return (float) tanh(argument);
    }

    @Override
    public float calculateDerivative(float argument) {
        float tanh = (float) tanh(argument);
        return 1.0f - tanh*tanh;
    }
}
