package Logic.NeuronStructures;

import Logic.ActivationFunctions.ActivationFunctionChoice;
import Logic.Neurons.Neuron;

import java.util.ArrayList;

public class NeuronLayer {
    private ArrayList<Neuron> layer;
    private int inputSize;

    public NeuronLayer(){}

    public NeuronLayer(int size, int inputSize, float weightsRange, ActivationFunctionChoice activationFunction){
        layer = new ArrayList<>();
        this.inputSize = inputSize;
        Neuron.setRange(weightsRange);
        for(int i=0; i < size; i++){
            layer.add(new Neuron(inputSize,activationFunction.select()));
        }
    }

    public ArrayList<Neuron> getNeurons(){
        return layer;
    }

    public ArrayList<Float> propagateForward(ArrayList<Float> outputFromPreviousLayer){
        ArrayList<Float> results = new ArrayList<>();
        for(int i = 0; i<layer.size(); i++){
            results.add(layer.get(i).calculateOutput(outputFromPreviousLayer));
        }
        return results;
    }

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

    public void applyModifications(ArrayList<ArrayList<Float>> arrayLists) {
        for(int i = 0; i < layer.size();i++){
            layer.get(i).applyModifications(arrayLists.get(i));
        }
    }
}
