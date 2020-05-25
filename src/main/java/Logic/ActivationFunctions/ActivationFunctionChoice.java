package Logic.ActivationFunctions;

/**
 * Enum class which gathers the implemented activation functions for easier use.
 */
public enum ActivationFunctionChoice {
    ReLU("ReLU", new ReLU()),
    TanH("tanh", new Tanh()),
    Sigmoid("sigmoid", new Sigmoid()),
    Identity("identity", new Identity());

    /**
     * The description of the function (i.e. name of such function).
     */
    private String text;
    /**
     * The activation function described by the enum object.
     */
    private ActivationFunction function;

    /**
     * Constructs an enum object describing particular function type.
     * @param text -- description, i.e. name of the function
     * @param function -- an activation function for the enum instance
     */
    ActivationFunctionChoice(String text, ActivationFunction function){
        this.text = text;
        this.function = function;
    }

    /**
     * Returns the reference for the chosen activation function.
     * @return the reference for the chosen activation function.
     */
    public ActivationFunction select() {
        return function;
    }

    public String toString(){
        return text;
    }
}
