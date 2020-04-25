package Logic.ActivationFunctions;

public enum ActivationFunctionChoice {
    ReLU("Rectified Linear Unit", new ReLU()),
    TanH("Hyperbolic Tangent", new Tanh()),
    Sigmoid("Sigmoid", new Sigmoid()),
    Identity("Identity", new Identity());


    private String text;
    private ActivationFunction function;
    ActivationFunctionChoice(String text, ActivationFunction function){
        this.text = text;
        this.function = function;
    }

    public ActivationFunction select() {
        return function;
    }
}
