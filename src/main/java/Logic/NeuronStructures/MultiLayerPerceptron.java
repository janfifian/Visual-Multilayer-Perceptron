package Logic.NeuronStructures;

import Logic.Neurons.Neuron;

import java.util.ArrayList;

public class MultiLayerPerceptron {
    private ArrayList<NeuronLayer> layers;
    private float learningCoefficient;

    public MultiLayerPerceptron(ArrayList<NeuronLayer> layers, float learningCoefficient){
        this.layers = layers;
        this.learningCoefficient = learningCoefficient;
        Neuron.learningCoefficient = learningCoefficient;
    }

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

}
