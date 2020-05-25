package Logic.NeuronStructures;

import Logic.ActivationFunctions.ActivationFunctionChoice;
import Logic.Neurons.Neuron;

import java.util.ArrayList;

/**
 * Class representing a single layer in the neural network.
 */
public class NeuronLayer {
    /**
     * ArrayList containing all the neurons from the particular layer.
     */
    private ArrayList<Neuron> layer;
    /**
     * Size of the input for all neurons in the given layer.
     */
    private int inputSize;

    /**
     * Chosen activation function for this layer.
     */
    private ActivationFunctionChoice activationFunctionChoice;

    /**
     * Constructs an empty neuron layer.
     */
    public NeuronLayer(){
        this.layer = new ArrayList<>();
    }

    /**
     * Constructs the neuron layer with given presets, common for each neuron contributing to the layer.
     * @param size number of neurons from which this layer consists
     * @param inputSize size of the input for each neuron, usually the size of the previous layer
     * @param weightsRange range of weights, which will be used to initialize the neurons
     * @param activationFunction common activation function for all the neurons in this layer
     */
    public NeuronLayer(int size, int inputSize, float weightsRange, ActivationFunctionChoice activationFunction){
        layer = new ArrayList<>();
        this.inputSize = inputSize;
        Neuron.setRange(weightsRange);
        activationFunctionChoice = activationFunction;
        for(int i=0; i < size; i++){
            layer.add(new Neuron(inputSize,activationFunction.select()));
        }
    }

    /**
     * Returns the ArrayList of neurons.
     * @return ArrayList containing all the neurons.
     */
    public ArrayList<Neuron> getNeurons(){
        return layer;
    }

    /**
     * Calculates the output for each neuron and returns the ArrayList of results.
     * @param outputFromPreviousLayer as the name suggests, it is the input for this particular layer object,
     *                                which usually happens to be the output of the previous layer
     * @return Array of the computed by each neuron values for the provided input
     */
    public ArrayList<Float> propagateForward(ArrayList<Float> outputFromPreviousLayer){
        ArrayList<Float> results = new ArrayList<>();
        for(int i = 0; i<layer.size(); i++){
            results.add(layer.get(i).calculateOutput(outputFromPreviousLayer));
        }
        return results;
    }

    /**
     * Calculates the deltas in the hidden (inner) layers of the neural network.
     * @param previouslyCalculatedLayer The layer which supersedes this layer in the neural network
     *                                  i.e. if the input layer has the number 0 and the output layer has the index L
     *                                  then for i-th layer this parameter should be equal to (i+1)-th layer
     * @param deltasArray deltas array for the (i+1)-th layer
     * @return deltas array for the i-th layer
     */
    public ArrayList<ArrayList<Float> > calculateHiddenLayerDeltasVector(NeuronLayer previouslyCalculatedLayer, ArrayList<ArrayList<Float > > deltasArray){
        ArrayList<ArrayList<Float> > deltas = new ArrayList<ArrayList<Float> >();
        for(int k = 0; k < layer.size(); k++){
            deltas.add(new ArrayList<>());
            for(int i = 0; i < inputSize; i++){
                deltas.get(k).add(layer.get(k).calculateInnerDelta(previouslyCalculatedLayer, deltasArray,k,i));
            }
            deltas.get(k).add(layer.get(k).calculateInnerBiasDelta(previouslyCalculatedLayer, deltasArray,k));
        }
        return deltas;
    }

    /**
     * Calculates the deltas for neurons in the outermost layer.
     * @param actualOutput output obtained from the neural network
     * @param expectedOutput actual output from the data sample
     * @return ArrayList containing arrays of deltas to be applied for each neuron (aka deltas array).
     */
    public ArrayList<ArrayList<Float> > calculateOuterDeltasVector(ArrayList<Float> actualOutput, ArrayList<Float> expectedOutput) {
        ArrayList<ArrayList<Float> > deltas = new ArrayList<ArrayList<Float> >();
        for(int k = 0; k < layer.size(); k++){
            deltas.add(new ArrayList<>());
            for(int i = 0; i < inputSize; i++){
                deltas.get(k).add(layer.get(k).calculateOuterDelta(actualOutput.get(k), expectedOutput.get(k),i));
            }
            deltas.get(k).add(layer.get(k).calculateOuterBiasDelta(actualOutput.get(k),expectedOutput.get(k)));
        }
        return deltas;
    }

    /**
     * Applies the modifications to each neuron.
     * @param arrayLists ArrayLists of lists of modifications for the weights of each neuron.
     */
    public void applyModifications(ArrayList<ArrayList<Float>> arrayLists) {
        for(int i = 0; i < layer.size();i++){
            layer.get(i).applyModifications(arrayLists.get(i));
        }
    }

    /**
     * Converts the NeuronLayer to Maple notation, so it can be used in the Maple2019 as a normal function.
     * Unless You are planning to use Maple to work further with the obtained neural network,
     *
     * @return Maple notation of the function representing this particular neuron layer.
     */
    public String toMapleString(int layerNumber) {
        StringBuilder sb = new StringBuilder();
        StringBuilder box = new StringBuilder();
        box.append("x_0");
        for(int i = 1; i < inputSize;i++){
            box.append(",x_"+i);
        }

        for(int i = 0; i < layer.size() ;i++){
            sb.append("Neuron"+layerNumber+"_"+i+":=(")
              .append(box.toString()+")->")
              .append(activationFunctionChoice.toString())
              .append("(DotProduct(["+box+"],")
              .append(layer.get(i).toMapleString())
              .append(")+")
              .append(layer.get(i).getBias())
              .append(");\n");
        }

        sb.append("NeuronLayer"+layerNumber+":=("+box+ ")->(Neuron"+layerNumber+"_0("+box+")");
        for(int i = 1; i < layer.size(); i++){
            sb.append(",Neuron"+layerNumber+"_"+i+"("+box+")");
        }
        sb.append(")" +";\n");
        return sb.toString();
    }
}
