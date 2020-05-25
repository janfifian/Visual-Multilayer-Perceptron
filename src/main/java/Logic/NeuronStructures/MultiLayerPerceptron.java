package Logic.NeuronStructures;

import Logic.Neurons.Neuron;

import java.util.ArrayList;

/**
 * A class representing the Neural Network itself.
 */
public class MultiLayerPerceptron {
    /**
     * Layers of the neural network
     */
    private ArrayList<NeuronLayer> layers;

    /**
     * Constructs a neural network consisting of provided layers of neurons and a learning coefficient.
     * @param layers Layers of neurons. It is assumed, that these layers are compatible,
     *               i.e. neurons from the i-th layer take as many arguments as the previous layers produces
     * @param learningCoefficient the learning coefficient
     */
    public MultiLayerPerceptron(ArrayList<NeuronLayer> layers, float learningCoefficient){
        this.layers = layers;
        Neuron.learningCoefficient = learningCoefficient;
    }

    /**
     * Calculates the output for a neural network based on the provided arguments.
     * @param input The values provided as a base for calculations
     * @return The result of calculations
     */
    public ArrayList<Float> calculateOutput(ArrayList<Float> input){
        ArrayList<Float> result = input;
        for(int i = 0; i < layers.size(); i++ ){
            result = layers.get(i).propagateForward(result);
        }
        return result;
    }

    /**
     * Performs the backpropagation according to the provided error array.
     * @param actualOutput Array of obtained output.
     * @param expectedOutput Array of expected output.
     */
    public void modifyWeights(ArrayList<Float> actualOutput, ArrayList<Float> expectedOutput){
        ArrayList<ArrayList<ArrayList<Float> > > modifications = new ArrayList<>();
        ArrayList<ArrayList<Float> > deltasVector = layers.get(layers.size()-1).calculateOuterDeltasVector(actualOutput,expectedOutput);
        modifications.add(deltasVector);
        for(int i = layers.size()-2; i>=0;i--){
            modifications.add(layers.get(i).calculateHiddenLayerDeltasVector(layers.get(i+1),modifications.get(modifications.size()-1)));
        }
        for(int i = 0; i < layers.size(); i++){
            layers.get(i).applyModifications(modifications.get(modifications.size()-1-i));
        }
    }

    /**
     * Returns the array of neuron layers forming the neural network.
     * @return a collection of NeuronLayers.
     */
    public ArrayList<NeuronLayer> getLayers(){
        return layers;
    }
}
