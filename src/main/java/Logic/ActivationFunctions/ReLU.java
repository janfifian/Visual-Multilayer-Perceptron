package Logic.ActivationFunctions;

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
