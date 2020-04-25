package Logic.ActivationFunctions;

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
