package Logic.Neurons;

import Logic.ActivationFunctions.ActivationFunction;
import Logic.NeuronStructures.NeuronLayer;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class representing the most important part of the neural network -- the neuron itself.
 */
public class Neuron {
    /**
     * Random number generator for weights initialization etc.
     */
    private static Random random = new Random();
    /**
     * ArrayList representing the weights associated with each input.
     */
    private ArrayList<Float> weights;
    /**
     * Bias for this particular neuron.
     */
    private float bias;
    /**
     * Activation function of a neuron
     */
    private ActivationFunction activationFunction;

    /**
     * The most recently obtained input.
     */
    private ArrayList<Float> recentlyObtainedInput;

    /**
     * The most recently calculated value for the "recentlyObtainedInput".
     */
    private float recentlyCalculatedValue;

    /**
     * Value which determines the (-f/2,f/2) range of initial neuron weights values.
     */
    private static float range = 2.0f;

    /**
     * The learning coefficient for all neurons.
     */
    public static float learningCoefficient;

    /**
     * Sets the range from  which the neuron weights are initialized.
     * @param f value, determining the (-f/2,f/2) range of initial neuron weights values.
     */
    public static void setRange(float f){
        range = f;
    };

    /**
     * Fixes the neuron random number generator seed for demonstration purposes.
     * This makes the initial weight values of each neuron (generated after the usage of this method) non-random.
     * @param l seed value
     */
    public static void setSeed(long l){
        random.setSeed(l);
    };

    /**
     * Static constructor which initializes the neuron
     */
    static{
        random.setSeed(System.currentTimeMillis());
    }

    /**
     * Constructs a new neuron with the given input size and activation function.
     * @param inputSize size of the input to be processed by neuron (typically, the size of the previous layer)
     * @param activationFunction the activation function for the neuron
     */
    public Neuron(int inputSize, ActivationFunction activationFunction){
        this.activationFunction = activationFunction;
        this.weights = new ArrayList<Float>();
        for(int i = 0; i < inputSize; i++){
            weights.add(random.nextFloat()*range - range*0.5f);
        }
        bias = random.nextFloat()*range - range*0.5f;
    }

    /**
     * Calculates the output in the standard way:
     * 1. The inner product of input and weight is calculated.
     * 2. The bias is added to the result.
     * 3. The activation function is applied.
     * @param input ArrayList representing the input values from previous layer.
     * @return calculated output of a neuron for a given input.
     */
    public float calculateOutput(ArrayList<Float> input){
        recentlyObtainedInput= input;
        float argument = Neuron.innerProduct(input,weights);
        recentlyCalculatedValue= activationFunction.calculate(argument+bias);
        return recentlyCalculatedValue;
    }


    /**
     * Updated the bias by subtracting the provided value from the previous one.
     * @param modification the value according to which the bias should be modified
     */
    public void shiftBias(float modification){
        bias-=modification;
    }

    /**
     * Returns the array of weights for the selected neuron.
     * @return array of weights for the particular neuron
     */
    public ArrayList<Float> getWeights(){
        return weights;
    }

    /**
     * Calculates the inner product of two ArrayLists.
     * This method does not check for the equal size of the vectors A and B.
     * @param A First argument for the inner product
     * @param B Second argument for the inner product
     * @return calculated value of the standard inner product in R^n (note that the order of the arguments does not matter).
     */
    private static float innerProduct(ArrayList<Float> A, ArrayList<Float> B){
        float result = 0.0f;
        for(int i = 0; i < A.size(); i++){
            result += A.get(i) * B.get(i);
        }
        return result;
    }

    /**
     * Returns the most recently calculated value.
     * @return the value calculated lastly
     */
    public float getRecentlyCalculatedValue(){
        return recentlyCalculatedValue;
    }

    /**
     * Calculate a single delta for i-th input weight modification in the case when the neuron lies in the outermost layer.
     *  If we assume the following notions:
     *         P(x) -- neuron output for the input vector x (where input is provided by the previous layer)
     *                 In fact, P(x) = f( x . w + b), where
     *         b -- bias of the neuron
     *         w -- weights array of the neuron ( "." denotes the inner product)
     *         x_i -- i-th coordinate of the input
     *         y -- expected output
     *         f -- neuron activation function
     *
     *         then the outermost delta for i-th input is given by the formula
     *
     *         delta_i = ( P(x) - y ) *  f' ( x.w + b ) * x_i
     *
     * @param actualOutput output produced by the neuron
     * @param expectedOutput expected value (taken from the data sample)
     * @param index index "i" of the input variable
     * @return delta for the i-th input weight.
     */

    public float calculateOuterDelta(float actualOutput, float expectedOutput,int index) {
        return (actualOutput - expectedOutput)*activationFunction.calculateDerivative(innerProduct(recentlyObtainedInput,weights)+bias)*recentlyObtainedInput.get(index);
    }

    /**
     * Calculate the bias delta for bias modification in the case when the neuron lies in the outermost layer.
     *  If we assume the following notions:
     *         P(x) -- neuron output for the input vector x (where input is provided by the previous layer)
     *                 In fact, P(x) = f( x . w + b), where
     *         b -- bias of the neuron
     *         w -- weights array of the neuron ( "." denotes the inner product)
     *         y -- expected output
     *         f -- neuron activation function
     *
     *         then the outermost bias delta is given by the formula
     *
     *         delta = ( P(x) - y ) *  f' ( x.w + b )
     *
     * @param actualOutput output produced by the neuron
     * @param expectedOutput expected value (taken from the data sample)
     * @return delta for the bias.
     */

    public float calculateOuterBiasDelta(float actualOutput, float expectedOutput){
        return (actualOutput - expectedOutput)*activationFunction.calculateDerivative(innerProduct(recentlyObtainedInput,weights)+bias);
    }

    /**
     *    Calculate a single delta for i-th input weight modification in the case when the neuron lies in one of the inner layers.
     *    If we assume the following notions:
     *         P(x) -- this neuron output for the input vector x
     *                 (where input is provided by the previous layer)
     *                 In fact, P(x) = f( x . w + b), where
     *         b -- bias of the neuron
     *         w -- weights array of the neuron ( "." denotes the inner product)
     *         x_i -- i-th coordinate of the input
     *         y -- expected output
     *         f -- neuron activation function
     *         j -- index of this neuron in its layer
     *
     *         then the outermost delta for i-th input is given by the formula
     *
     *         delta_i = Sum *  f' ( x.w + b ) * x_i,
     *
     *         where the Sum variable denotes the sum of the influences from the superseding layer,
     *         each of the form (where k is the index of the influencing neuron from the superseding layer)
     *         and delta denotes the bias delta of the previous layer (since it does not contains multiplication
     *         by argument)
     *
     *         Influence_k = delta*w_k(j);
     *
     * @param previouslyCalculatedLayer neuron layer superseding the layer this neuron belongs to;
     *                                  i.e. if this particular neuron belongs to the j-th layer,
     *                                  then this argument should equal to the (j+1)-th layer.
     * @param deltasArray array of deltas from the superseding layer
     * @param ownIndex index of this neuron in the layer it belongs to
     * @param relativeIndex index of the input variable, with respect to which the delta is calculated
     * @return
     */
    public float calculateInnerDelta(NeuronLayer previouslyCalculatedLayer, ArrayList<ArrayList<Float>> deltasArray, int ownIndex, int relativeIndex) {
        float derivative = activationFunction.calculateDerivative(innerProduct(recentlyObtainedInput,weights)+bias);
        float sum = 0.0f;
        for(int i = 0; i < previouslyCalculatedLayer.getNeurons().size(); i++){
            sum+=deltasArray.get(i).get(deltasArray.get(i).size()-1)*previouslyCalculatedLayer.getNeurons().get(i).getWeights().get(ownIndex);
        }
        return derivative*sum*recentlyObtainedInput.get(relativeIndex);
    }

    public Float calculateInnerBiasDelta(NeuronLayer previouslyCalculatedLayer, ArrayList<ArrayList<Float>> deltasArray, int ownIndex) {
        float derivative = activationFunction.calculateDerivative(innerProduct(recentlyObtainedInput,weights)+bias);
        float sum = 0.0f;
        for(int i = 0; i < previouslyCalculatedLayer.getNeurons().size(); i++){
            sum+=deltasArray.get(i).get(deltasArray.get(i).size()-1)*previouslyCalculatedLayer.getNeurons().get(i).getWeights().get(ownIndex);
        }
        return derivative*sum;
    }

    /**
     * Applies the array of modification, where i-th value affects the value of the i-th input weight.
     * Last value modifies the bias.
     * These modifications are basically just deltas multiplied by the learning coefficient.
     * @param modification array of modifications for the weights of this particular neuron and one modification value for the bias
     */
    public void applyModifications(ArrayList<Float> modification) {
        for(int i =0; i < weights.size(); i++){
            weights.set(i, weights.get(i)-learningCoefficient*modification.get(i));
        }
        shiftBias(learningCoefficient*modification.get(modification.size()-1));
    }

    /**
     * Generates part of a function regarding this neuron, namely vector of weights needed for the dot product.
     * @return string describing the array of weights
     */
    public String toMapleString() {
        StringBuilder sb = new StringBuilder();
        sb.append("["+weights.get(0));
        for(int i = 1; i < weights.size(); i++){
            sb.append(',').append(weights.get(i));
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Returns the bias in the form of string.
     * @return string representing the value of bias
     */
    public String getBias() {
        return ("("+ Double.toString(this.bias)+")");
    }
}
